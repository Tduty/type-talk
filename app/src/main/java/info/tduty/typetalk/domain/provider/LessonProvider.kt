package info.tduty.typetalk.domain.provider

import info.tduty.typetalk.api.LessonApi
import info.tduty.typetalk.data.dto.CreateDialogDTO
import info.tduty.typetalk.data.dto.LessonDTO
import info.tduty.typetalk.data.dto.TeacherLessonDTO
import info.tduty.typetalk.data.dto.TeacherTaskDTO
import info.tduty.typetalk.data.pref.TokenStorage
import io.reactivex.Completable
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

    fun getTeacherLessons(classId: String): Observable<List<TeacherLessonDTO>> {
        return lessonApi.getTeacherLessons(tokenStorage.getToken(), classId)
    }

    fun getTeacherTasks(lessonId: String, classId: String): Observable<List<TeacherTaskDTO>> {
        return lessonApi.getTeacherTasks(tokenStorage.getToken(), lessonId, classId)
    }

    fun createDialogs(lessonId: String, taskId: String, studentIds: List<String>): Completable {
        return lessonApi.createDialogs(
            tokenStorage.getToken(),
            CreateDialogDTO(lessonId, taskId, studentIds)
        )
    }
}
