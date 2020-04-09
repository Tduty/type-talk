package info.tduty.typetalk.domain.interactor

import info.tduty.typetalk.R
import info.tduty.typetalk.data.db.model.ChatEntity
import info.tduty.typetalk.data.db.wrapper.ChatWrapper
import info.tduty.typetalk.data.dto.ChatDTO
import info.tduty.typetalk.data.model.ChatVO
import info.tduty.typetalk.domain.provider.ChatProvider
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 08.03.2020.
 */
class ChatInteractor(
    private val chatWrapper: ChatWrapper,
    private val chatProvider: ChatProvider
) {

    fun getChat(chatId: String): Observable<ChatVO> {
        return chatWrapper.getByChatId(chatId)
            .map { toVO(it) }
            .switchIfEmpty(
                chatProvider.getChat(chatId)
                    .map { toVO(it) }
            )
    }

    fun getTeacherChat(): Observable<ChatVO> {
        return chatWrapper.getTeacherChat()
            .doOnError { Timber.e(it) }
            .map { toVO(it) }
            .switchIfEmpty(
                chatProvider.getChatTeacher()
                    .doOnError { Timber.e(it) }
                    .flatMap { dto ->
                        chatWrapper.insert(toDB(dto))
                            .andThen(Observable.just(dto))
                            .map { toVO(it) }
                    }
            )
    }

    fun getClassChat(): Observable<ChatVO> {
        return chatWrapper.getClassChat()
            .map { toVO(it) }
            .switchIfEmpty (
                chatProvider.getChatClass()
                    .doOnError { Timber.e(it) }
                    .flatMap { dto ->
                        chatWrapper.insert(toDB(dto))
                            .andThen(Observable.just(dto))
                            .map { toVO(it) }
                    }
            )
    }

    fun loadChats(): Completable {
        return chatProvider.getChats()
            .flatMapCompletable { chats ->
                chatWrapper.insert(chats.map { toDB(it) })
            }
    }

    fun loadAllChats(): Observable<List<String>> {
        return chatProvider.getChats()
            .flatMap { chats ->
                chatWrapper.insert(chats.map { toDB(it) })
                    .andThen(Observable.just(chats.map { it.id }))
            }
    }

    private fun toVO(db: ChatEntity): ChatVO {
        return ChatVO(
            chatId = db.chatId,
            title = db.title,
            type = db.type,
            avatarURL = getIconByType(db.type),
            description = db.description,
            isTeacherChat = db.type == ChatEntity.TEACHER_CHAT
        )
    }

    private fun toVO(dto: ChatDTO): ChatVO {
        return ChatVO(
            chatId = dto.id,
            title = dto.title,
            type = dto.type,
            avatarURL = getIconByType(dto.type),
            description = dto.description ?: "",
            isTeacherChat = dto.type == ChatEntity.TEACHER_CHAT
        )
    }

    private fun toDB(dto: ChatDTO): ChatEntity {
        return ChatEntity(
            chatId = dto.id,
            title = dto.title,
            type = dto.type,
            imageURL = dto.icon,
            description = dto.description ?: ""
        )
    }

    private fun getIconByType(type: String): Int {
        return when (type) {
            ChatEntity.TEACHER_CHAT -> R.drawable.ic_teacher_bg_varden
            ChatEntity.CLASS_CHAT -> R.drawable.ic_class_bg_varden
            else -> R.drawable.ic_boy_bg_varden
        }
    }
}