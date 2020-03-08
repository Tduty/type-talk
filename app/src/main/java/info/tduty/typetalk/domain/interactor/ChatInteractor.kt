package info.tduty.typetalk.domain.interactor

import info.tduty.typetalk.data.db.model.ChatEntity
import info.tduty.typetalk.data.db.wrapper.ChatWrapper
import info.tduty.typetalk.data.dto.ChatDTO
import info.tduty.typetalk.data.model.ChatVO
import info.tduty.typetalk.domain.provider.ChatProvider
import io.reactivex.Completable
import io.reactivex.Observable

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

    fun loadChats(): Completable {
        return chatProvider.getChats()
            .flatMapCompletable { chats ->
                chatWrapper.insert(chats.map { toDB(it) })
            }
    }

    private fun toVO(db: ChatEntity): ChatVO {
        return ChatVO(
            chatId = db.chatId,
            title = db.title,
            avatarURL = db.imageURL,
            description = db.description
        )
    }

    private fun toVO(dto: ChatDTO): ChatVO {
        return ChatVO(
            chatId = dto.id,
            title = dto.title,
            avatarURL = dto.icon,
            description = dto.description
        )
    }

    private fun toDB(dto: ChatDTO): ChatEntity {
        return ChatEntity(
            chatId = dto.id,
            title = dto.title,
            imageURL = dto.icon,
            description = dto.description
        )
    }
}