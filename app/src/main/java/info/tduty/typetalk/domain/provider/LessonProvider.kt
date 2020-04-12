package info.tduty.typetalk.domain.provider

import info.tduty.typetalk.api.LessonApi
import info.tduty.typetalk.data.dto.LessonDTO
import info.tduty.typetalk.data.pref.TokenStorage
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
class LessonProvider(
    private val lessonApi: LessonApi,
    private val tokenStorage: TokenStorage
) {

    fun getLessons(): Observable<List<LessonDTO>> {
        return lessonApi.getLessons(tokenStorage.getToken())
    }

    fun getLesson(lessonId: String): Observable<LessonDTO> {
        return lessonApi.getLesson(tokenStorage.getToken(), lessonId)
    }
}
