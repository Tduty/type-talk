package info.tduty.typetalk.domain.provider

import info.tduty.typetalk.api.LessonApi
import info.tduty.typetalk.data.dto.LessonDTO
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
class LessonProvider(private val lessonApi: LessonApi) {

    fun getLessons(): Observable<List<LessonDTO>> {
        return lessonApi.getLessons()
    }
}
