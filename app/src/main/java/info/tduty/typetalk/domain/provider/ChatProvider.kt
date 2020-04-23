package info.tduty.typetalk.domain.provider

import info.tduty.typetalk.api.ChatApi
import info.tduty.typetalk.data.dto.ChatDTO
import info.tduty.typetalk.data.pref.TokenStorage
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 08.03.2020.
 */
class ChatProvider(
    private val chatApi: ChatApi,
    private val tokenStorage: TokenStorage
) {

    fun getChats(): Observable<List<ChatDTO>> {
        return chatApi.getChats(tokenStorage.getToken())
    }

    fun getChat(chatId: String): Observable<ChatDTO> {
        return chatApi.getChat(tokenStorage.getToken(), chatId)
    }

    fun getChatTeacher(): Observable<ChatDTO> {
        return chatApi.getChatTeacher(tokenStorage.getToken())
    }

    fun getChatClass(): Observable<ChatDTO> {
        return chatApi.geChatClass(tokenStorage.getToken())
    }

    fun getTaskChat(lessonId: String, taskId: String): Observable<List<ChatDTO>> {
        return chatApi.getTaskChats(tokenStorage.getToken(), lessonId, taskId)
    }
}
