package info.tduty.typetalk.data.db.dao

import androidx.room.*
import info.tduty.typetalk.data.db.model.TaskEntity
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(message: TaskEntity?): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(message: List<TaskEntity>?): Completable

    @Query("UPDATE task SET payload = :payload WHERE task_id = :taskId")
    fun updatePayload(taskId: String, payload: String): Completable

    @Query("SELECT * FROM task")
    fun getAllTasks(): List<TaskEntity?>?

    @Query("SELECT * FROM task WHERE lessons_id = :lessonsId")
    fun getAllTasksByLessonId(lessonsId: String): Maybe<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE task_id = :taskId")
    fun getTaskById(taskId: String): Maybe<TaskEntity>

    @Query("SELECT * FROM task WHERE id = :id")
    fun getTask(id: Long?): TaskEntity?

    @Update
    fun update(task: TaskEntity?)

    @Query("UPDATE task SET is_preformed = :isPerformed WHERE task_id = :id")
    fun updateState(id: String, isPerformed: Boolean): Completable

    @Delete
    fun delete(task: TaskEntity?)
}
