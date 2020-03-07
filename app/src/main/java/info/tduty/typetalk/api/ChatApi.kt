package info.tduty.typetalk.api

import info.tduty.typetalk.data.dto.ChatDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Evgeniy Mezentsev on 08.03.2020.
 */
interface ChatApi {

    @GET("chat")
    fun getChats(): Observable<List<ChatDTO>>

    @GET("chat/{chat_id}")
    fun getChat(@Path("chat_id") chatId: String): Observable<ChatDTO>
}