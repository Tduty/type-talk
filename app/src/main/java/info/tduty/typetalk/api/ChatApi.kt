package info.tduty.typetalk.api

import info.tduty.typetalk.data.dto.ChatDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * Created by Evgeniy Mezentsev on 08.03.2020.
 */
interface ChatApi {

    companion object {
        const val ENDPOINT_PREFIX = "/chat/"
    }

    @GET(".")
    fun getChats(@Header("Authorization") token: String): Observable<List<ChatDTO>>

    @GET("{chat_id}")
    fun getChat(
        @Header("Authorization") token: String,
        @Path("chat_id") chatId: String
    ): Observable<ChatDTO>

    @GET("teacher")
    fun getChatTeacher(@Header("Authorization") token: String): Observable<ChatDTO>

    @GET("class")
    fun geChatClass(@Header("Authorization") token: String): Observable<ChatDTO>
}