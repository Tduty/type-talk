package info.tduty.typetalk.domain.interactor

import info.tduty.typetalk.R
import info.tduty.typetalk.data.db.model.MessageEntity
import info.tduty.typetalk.data.db.wrapper.MessageWrapper
import info.tduty.typetalk.data.dto.MessageDTO
import info.tduty.typetalk.data.event.payload.MessageNewPayload
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
    private val userDataHelper: UserDataHelper,
    private val eventManager: EventManager,
    private val socketController: SocketController
) {

    fun getHistory(chatId: String): Observable<List<MessageVO>> {
        return historyProvider.getHistory(chatId)
            .flatMap { messages ->
                val dbList = messages.map { toDB(it) }
                val voList = dbList.map { toVO(it) }
                messageWrapper.insert(dbList)
                    .andThen(Observable.just(voList))
            }
    }

    fun addMessage(message: MessageNewPayload): Completable {
        val db = toDB(message)
        return messageWrapper.insert(db)
            .doOnComplete { eventManager.post(toVO(db)) }
    }

    fun sendMessage(chatId: String, message: String) {
        try {
            socketController.sendMessageNew(toPayload(chatId, message))
        } catch (ex: Exception) {
            Timber.e(ex, "Error sending message: $message in chat $chatId")
        }
    }

    private fun toDB(dto: MessageDTO): MessageEntity {
        return MessageEntity(
            syncId = dto.id,
            title = dto.senderName, //TODO добавить в DTO name в будущем выпилится
            content = dto.body,
            chatId = dto.chatId,
            avatarURL = "cl_your_teacher_chat", //TODO придумать норм способ добавлять аватарку
            isMy = dto.senderId == userDataHelper.getSavedUser().id
        )
    }

    private fun toDB(payload: MessageNewPayload): MessageEntity {
        return MessageEntity(
            syncId = payload.id,
            title = payload.senderName, //TODO добавить в DTO name в будущем выпилится
            content = payload.body,
            chatId = payload.chatId,
            avatarURL = "cl_your_teacher_chat", //TODO придумать норм способ добавлять аватарку
            isMy = payload.senderId == userDataHelper.getSavedUser().id
        )
    }

    private fun toVO(db: MessageEntity): MessageVO {
        return MessageVO(
            id = db.syncId,
            chatId = db.chatId ?: "",
            type = MessageVO.Type.MESSAGE,
            isMy = db.isMy,
            showSender = true, //TODO получать объект чата и для группового не показывать имя
            senderName = db.title,
            message = db.content,
            avatar = R.drawable.ic_teacher
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