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

    fun getTaskForLesson(lessonsId: Long, type: TaskType): Observable<List<TaskPayloadVO>> {
        val lessonsId = lessonsId
        val type = type
        val taskVO = TaskVO(
            id = "1",
            type = TaskType.FLASHCARDS, // TODO: realize parsing type task
            icon = R.drawable.ic_teacher, // TODO: setup icon
            title = "",
            payload =  """
                    [
                       {
                            "type": "eng-rus",
                            "front": "test1",
                            "back": "тест1"
                        },
                        {
                            "type": "rus-eng",
                            "front": "тест2",
                            "back": "test2"
                        },
                        {
                            "type": "rus-eng",
                            "front": "тест3",
                            "back": "test3"
                        },
                        {
                            "type": "rus-eng",
                            "front": "тест4",
                            "back": "test4"
                        }
                	]
            """.trimIndent(),
            optional = false,
            checked = false
        )
        val taskPayloadVO = toTaskPayloadVO(taskVO)
        return Observable.just(taskPayloadVO)
        /*
        return taskWrapper.getTasksForLesson(lessonsId)
            .flatMap { taskList ->
                val voList = taskList.map { toVO(it) }
                val taskPayloadVO = toTaskPayloadVO(voList.first { taskVO -> taskVO.type == type })

                if (taskPayloadVO.isEmpty()) {
                    throw Exception()
                }

                Observable.just(taskPayloadVO)
            }
            /
         */
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
            optional = db.optional,
            checked = db.isPerformed
        )
    }
}