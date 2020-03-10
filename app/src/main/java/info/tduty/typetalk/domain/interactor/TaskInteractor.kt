package info.tduty.typetalk.domain.interactor

import info.tduty.typetalk.data.db.model.TaskEntity
import info.tduty.typetalk.data.db.wrapper.TaskWrapper
import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.data.model.TaskVO
import io.reactivex.Observable

class TaskInteractor(
    private val taskWrapper: TaskWrapper
) {

    fun getTasksForLesson(lessonsId: Long): Observable<List<TaskVO>> {
        return taskWrapper.getTasksForLesson(lessonsId)
            .flatMap { taskList ->
                val voList = taskList.map { toVO(it) }
                Observable.just(voList)
            }
    }

    private fun toVO(db: TaskEntity): TaskVO {
        return TaskVO(
            id = db.id.toString(),
            type = TaskType.DICTIONARY_PICTIONARY, // TODO: realize parsing type task
            icon = -1, // TODO: setup icon
            title = db.title ?: "",
            optional = false, // TODO: added new parameter for entity
            checked = db.isPerformed ?: false
        )
    }
}