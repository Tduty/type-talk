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

    @Query("SELECT * FROM task")
    fun getAllTasks(): List<TaskEntity?>?

    @Query("SELECT * FROM task WHERE lessons_id = :lessonsId")
    fun getAllTasksByLessonId(lessonsId: String): Maybe<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE id =:id")
    fun getTask(id: Long?): TaskEntity?

    @Update
    fun update(task: TaskEntity?)

    @Delete
    fun delete(task: TaskEntity?)
}
