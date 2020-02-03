package info.tduty.typetalk.data.dao

import androidx.room.*
import info.tduty.typetalk.data.entity.TaskEntity

@Dao
interface TaskDao {
    @Insert
    fun insert(message: TaskEntity?):Long?

    @Query("SELECT * FROM task ORDER BY id DESC")
    fun getAllTasks():List<TaskEntity?>?

    @Query("SELECT * FROM task WHERE id =:id")
    fun getTask(id: Long?):TaskEntity?

    @Update
    fun update(task:TaskEntity?)

    @Delete
    fun delete(task:TaskEntity?)
}