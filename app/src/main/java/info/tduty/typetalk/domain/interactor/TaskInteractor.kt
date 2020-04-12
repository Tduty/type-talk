package info.tduty.typetalk.domain.interactor

import info.tduty.typetalk.R
import info.tduty.typetalk.data.db.model.TaskEntity
import info.tduty.typetalk.data.db.wrapper.TaskWrapper
import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.data.model.TaskVO
import io.reactivex.Observable

class TaskInteractor(
    private val taskWrapper: TaskWrapper
) {

    fun getTasks(lessonsId: String): Observable<List<TaskVO>> {
        return taskWrapper.getTasksByLessonId(lessonsId)
            .map { tasks ->
                tasks.sortedBy { it.position }
                .map { toVO(it) }
            }
    }

    private fun toVO(db: TaskEntity): TaskVO {
        return TaskVO(
            id = db.taskId,
            type = TaskType.fromDbType(db.type),
            icon = R.drawable.ic_teacher, // TODO: setup icon
            title = db.title,
            optional = db.optional,
            checked = db.isPerformed
        )
    }
}