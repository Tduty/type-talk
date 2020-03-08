package info.tduty.typetalk.domain.provider

import info.tduty.typetalk.api.ChatApi
import info.tduty.typetalk.data.dto.ChatDTO
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 08.03.2020.
 */
class ChatProvider(private val chatApi: ChatApi) {

    fun getChats(): Observable<List<ChatDTO>> {
        return chatApi.getChats()
    }

    fun getChat(chatId: String): Observable<ChatDTO> {
        return chatApi.getChat(chatId)
    }
}
