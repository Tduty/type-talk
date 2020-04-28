package info.tduty.typetalk.domain.interactor

import info.tduty.typetalk.R
import info.tduty.typetalk.data.db.model.ChatEntity
import info.tduty.typetalk.data.db.model.MessageEntity
import info.tduty.typetalk.data.db.wrapper.ChatWrapper
import info.tduty.typetalk.data.db.wrapper.MessageWrapper
import info.tduty.typetalk.data.dto.MessageDTO
import info.tduty.typetalk.data.event.payload.CorrectionPayload
import info.tduty.typetalk.data.event.payload.MessageNewPayload
import info.tduty.typetalk.data.model.CorrectionVO
import info.tduty.typetalk.data.model.MessageVO
import info.tduty.typetalk.data.pref.UserDataHelper
import info.tduty.typetalk.domain.managers.EventManager
import info.tduty.typetalk.domain.provider.HistoryProvider
import info.tduty.typetalk.socket.SocketController
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber
import java.util.*

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
class HistoryInteractor(
    private val historyProvider: HistoryProvider,
    private val messageWrapper: MessageWrapper,
    private val chatWrapper: ChatWrapper,
    private val userDataHelper: UserDataHelper,
    private val eventManager: EventManager,
    private val socketController: SocketController
) {

    fun loadHistory(): Completable {
        return historyProvider.getHistory()
            .doOnError { Timber.e(it) }
            .flatMapCompletable { messages ->
                val dbList = messages.map { toDB(it) }
                messageWrapper.insert(dbList)
            }
    }

    fun loadHistory(chatId: String): Observable<List<MessageEntity>> {
        return historyProvider.getHistory(chatId)
            .doOnError { Timber.e(it) }
            .flatMap { messages ->
                val dbList = messages.map { toDB(it) }
                messageWrapper.insert(dbList)
                    .andThen(Observable.just(dbList))
            }
    }

    fun getHistory(chatId: String, chatType: String?): Observable<List<MessageVO>> {
        return messageWrapper.getByChatId(chatId)
            .map { messages ->
                messages
                    .sortedBy { it.sendingTime }
                    .map { toVO(it, chatType) }
            }
    }


    fun addMessage(message: MessageNewPayload): Completable {
        val db = toDB(message)
        return messageWrapper.insert(db)
            .andThen(chatWrapper.getByChatId(message.chatId))
            .doOnNext { eventManager.post(toVO(db, it.type)) }
            .ignoreElements()
    }

    fun handleCorrection(payload: CorrectionPayload): Completable {
        return messageWrapper.updateAdditional(
            payload.syncId,
            payload.additionalType,
            payload.additional
        )
            .doOnComplete {
                val vo = CorrectionVO(
                    payload.syncId,
                    payload.chatId,
                    payload.additional,
                    CorrectionVO.AdditionalType.intOf(payload.additionalType)
                )
                eventManager.post(vo)
            }
    }

    fun sendCorrection(syncId: String, chatId: String, text: String, type: CorrectionVO.AdditionalType) {
        try {
            val payload = CorrectionPayload(
                syncId,
                chatId,
                type.id,
                text
            )
            socketController.sendCorrection(payload)
        } catch (ex: Exception) {
            Timber.e(ex, "Error sending correction: $syncId in chat $chatId")
        }
    }

    fun sendMessage(chatId: String, message: String): String? {
        try {
            val payload = toPayload(chatId, message)
            socketController.sendMessageNew(payload)
            return payload.id
        } catch (ex: Exception) {
            Timber.e(ex, "Error sending message: $message in chat $chatId")
        }
        return null
    }

    private fun toDB(dto: MessageDTO): MessageEntity {
        return MessageEntity(
            syncId = dto.id,
            title = dto.senderName,
            content = dto.body,
            chatId = dto.chatId,
            avatarURL = "cl_your_teacher_chat", //TODO придумать норм способ добавлять аватарку
            senderType = dto.senderType,
            isMy = dto.senderId == userDataHelper.getSavedUser().id,
            additionalType = dto.additionalType,
            additional = dto.additional,
            sendingTime = dto.sendingTime
        )
    }

    private fun toDB(payload: MessageNewPayload): MessageEntity {
        return MessageEntity(
            syncId = payload.id,
            title = payload.senderName,
            content = payload.body,
            chatId = payload.chatId,
            avatarURL = "cl_your_teacher_chat", //TODO придумать норм способ добавлять аватарку
            senderType = payload.senderType ?: MessageEntity.SENDER_TYPE_MALE,
            isMy = payload.senderId == userDataHelper.getSavedUser().id,
            additionalType = payload.additionalType,
            additional = payload.additional,
            sendingTime = payload.sendingTime
        )
    }

    private fun toVO(db: MessageEntity, chatType: String?): MessageVO {
        return MessageVO(
            id = db.syncId,
            chatId = db.chatId ?: "",
            type = MessageVO.Type.MESSAGE,
            isMy = db.isMy,
            showSender = showSender(chatType),
            senderName = db.title,
            message = db.content,
            correction = toCorrectionVO(db),
            avatar = when (db.senderType) {
                MessageEntity.SENDER_TYPE_TEACHER -> R.drawable.ic_teacher_bubble
                MessageEntity.SENDER_TYPE_FEMALE -> R.drawable.ic_girl_bubble
                else -> R.drawable.ic_boy_bubble
            }
        )
    }

    private fun showSender(chatType: String?): Boolean {
        return if (chatType == ChatEntity.TASK_CHAT && userDataHelper.isTeacher()) true
        else chatType == ChatEntity.CLASS_CHAT || chatType == null
    }

    private fun toCorrectionVO(db: MessageEntity): CorrectionVO? {
        val additionalType = db.additionalType
        val additional= db.additional
        return if (additionalType == null || additional == null) null
        else CorrectionVO(
            db.syncId,
            db.chatId,
            additional,
            CorrectionVO.AdditionalType.intOf(additionalType)
        )
    }

    private fun toPayload(chatId: String, text: String): MessageNewPayload {
        return MessageNewPayload(
            id = UUID.randomUUID().toString(),
            chatId = chatId,
            senderName = userDataHelper.getSavedUser().surname,
            senderId = userDataHelper.getSavedUser().id,
            body = text
        )
    }
}