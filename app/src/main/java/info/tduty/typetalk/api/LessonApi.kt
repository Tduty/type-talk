package info.tduty.typetalk.api

import info.tduty.typetalk.data.dto.LessonDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
interface LessonApi {

    @GET("lessons")
    fun getLessons(@Header("Authorization") token: String): Observable<List<LessonDTO>>

    @GET("lessons/{lesson_id}")
    fun getLesson(
        @Header("Authorization") token: String,
        @Path("lesson_id") lessonId: String
    ): Observable<LessonDTO>
}