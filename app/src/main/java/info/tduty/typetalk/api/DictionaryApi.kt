package info.tduty.typetalk.api

import info.tduty.typetalk.data.dto.DictionaryDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Evgeniy Mezentsev on 09.04.2020.
 */
interface DictionaryApi {

    @GET("dictionary")
    fun getDictionary(
        @Header("Authorization") token: String,
        @Query("version") version: Int
    ): Observable<List<DictionaryDTO>>

    @GET("dictionary/{lesson_id}")
    fun getDictionary(
        @Header("Authorization") token: String,
        @Path("lesson_id") lessonId: String
    ): Observable<List<DictionaryDTO>>
}