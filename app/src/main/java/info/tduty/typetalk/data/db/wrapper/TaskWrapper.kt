package info.tduty.typetalk.data.db.wrapper

import info.tduty.typetalk.data.db.dao.TaskDao
import info.tduty.typetalk.data.db.model.TaskEntity
import info.tduty.typetalk.utils.Optional
import io.reactivex.Completable
import io.reactivex.Observable

class TaskWrapper(private val taskDao: TaskDao) {

    fun insert(task: TaskEntity): Completable {
        return taskDao.insert(task)
    }

    fun insert(tasks: List<TaskEntity>): Completable {
        return taskDao.insert(tasks)
    }

    fun updateState(taskId: String, isPerformed: Boolean): Completable {
        return taskDao.updateState(taskId, isPerformed)

    }

    fun updatePayload(taskId: String, payload: String): Completable {
        return taskDao.updatePayload(taskId, payload)
    }

    fun getTasksByLessonId(lessonsId: String): Observable<List<TaskEntity>> {
        return taskDao.getAllTasksByLessonId(lessonsId).toObservable()
    }

    fun getTasksById(taskId: String): Observable<Optional<TaskEntity>> {
        return taskDao.getTaskById(taskId)
            .map { Optional.of(it) }
            .toObservable()
            .defaultIfEmpty(Optional.empty())
    }
}