package info.tduty.typetalk.api

import info.tduty.typetalk.data.dto.LessonDTO
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
interface LessonApi {

    @GET("lessons")
    fun getLessons(): Observable<List<LessonDTO>>
}