package info.tduty.typetalk.api

import info.tduty.typetalk.data.dto.MessageDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
interface HistoryApi {

    @GET("history/{chat_id}")
    fun getHistory(
        @Header("Authorization") token: String,
        @Path("chat_id") chatId: String
    ): Observable<List<MessageDTO>>

    @GET("history")
    fun getHistory(
        @Header("Authorization") token: String
    ): Observable<List<MessageDTO>>
}
