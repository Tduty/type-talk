package info.tduty.typetalk.data.db.wrapper

import info.tduty.typetalk.data.db.dao.TaskDao
import info.tduty.typetalk.data.db.model.TaskEntity
import io.reactivex.Completable
import io.reactivex.Observable

class TaskWrapper(private val taskDao: TaskDao) {

    fun insert(task: TaskEntity): Completable {
        return taskDao.insert(task)
    }

    fun insert(tasks: List<TaskEntity>): Completable {
        return taskDao.insert(tasks)
    }

    fun getTasksByLessonId(lessonsId: String): Observable<List<TaskEntity>> {
        return taskDao.getAllTasksByLessonId(lessonsId).toObservable()
    }
}