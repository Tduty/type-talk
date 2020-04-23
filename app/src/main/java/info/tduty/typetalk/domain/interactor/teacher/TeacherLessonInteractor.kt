package info.tduty.typetalk.domain.interactor.teacher

import info.tduty.typetalk.R
import info.tduty.typetalk.data.dto.TeacherLessonDTO
import info.tduty.typetalk.data.dto.TeacherTaskDTO
import info.tduty.typetalk.data.model.LessonManageVO
import info.tduty.typetalk.data.model.TaskManageVO
import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.domain.interactor.HistoryInteractor
import info.tduty.typetalk.domain.provider.LessonProvider
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class TeacherLessonInteractor(
    private val lessonProvider: LessonProvider
) {

    fun loadAllLessons(classId: String): Observable<List<LessonManageVO>> {
        return lessonProvider.getTeacherLessons(classId)
            .map { lessons ->
                lessons.mapIndexed { index, dto -> dtoToVO(index, dto) }
            }
    }

    fun loadAllTasks(classId: String, lessonId: String): Observable<List<TaskManageVO>> {
        return lessonProvider.getTeacherTasks(lessonId, classId)
            .map { tasks -> tasks.sortedBy { it.position }.map { dtoToVO(it) } }
    }

    fun createDialogs(lessonId: String, taskId: String, studentIds: List<String>): Completable {
        return lessonProvider.createDialogs(lessonId, taskId, studentIds)
    }

    private fun dtoToVO(index: Int, dto: TeacherLessonDTO): LessonManageVO {
        return LessonManageVO(
            dto.id,
            index + 1,
            dto.title,
            R.drawable.ic_boy,
            dto.studentCount,
            dto.completed,
            dto.isLock
        )
    }

    private fun dtoToVO(dto: TeacherTaskDTO): TaskManageVO {
        return TaskManageVO(
            dto.id,
            dto.lessonId,
            dto.title,
            TaskType.fromDbType(dto.type),
            R.drawable.ic_boy,
            dto.studentCount,
            dto.completed
        )
    }
}