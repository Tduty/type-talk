package info.tduty.typetalk.domain.interactor

import info.tduty.typetalk.R
import info.tduty.typetalk.data.db.model.TaskEntity
import info.tduty.typetalk.data.db.wrapper.TaskWrapper
import info.tduty.typetalk.data.model.TaskPayloadVO
import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.mapper.TaskPayloadMapperStrategy
import io.reactivex.Observable
import java.lang.Exception
import java.util.*

class TaskInteractor(
    private val taskPayloadMapperStrategy: TaskPayloadMapperStrategy,
    private val taskWrapper: TaskWrapper
) {

    fun getTasks(lessonsId: String): Observable<List<TaskVO>> {
        return taskWrapper.getTasksByLessonId(lessonsId)
            .map { tasks ->
                tasks.sortedBy { it.position }
                .map { toVO(it) }
            }
    }

    fun getPayload(taskVO: TaskVO): Observable<List<TaskPayloadVO>> {
        return Observable.just(toTaskPayloadVO(taskVO))
    }

    fun getPayload2(taskVO: TaskVO): List<TaskPayloadVO> {
        return toTaskPayloadVO(taskVO)
    }

    private fun toTaskPayloadVO(first: TaskVO): List<TaskPayloadVO> {
        val mapper = taskPayloadMapperStrategy.getTaskPayloadMapper(first.type)
        return mapper?.map(first.payload) ?: Collections.emptyList()
    }

    private fun toVO(db: TaskEntity): TaskVO {
        return TaskVO(
            id = db.taskId,
            type = TaskType.fromDbType(db.type),
            icon = R.drawable.ic_teacher, // TODO: setup icon
            title = db.title,
            payload = db.payload ?: "",
            lessonId = db.lessonsId,
            optional = db.optional,
            checked = db.isPerformed
        )
    }
}