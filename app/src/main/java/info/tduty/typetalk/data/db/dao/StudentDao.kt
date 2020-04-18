package info.tduty.typetalk.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.tduty.typetalk.data.db.model.StudentEntity
import io.reactivex.Completable
import io.reactivex.Maybe

/**
 * Created by Evgeniy Mezentsev on 19.04.2020.
 */
@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(u: StudentEntity): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertList(u: List<StudentEntity>): Completable

    @Query("UPDATE student SET status = :status WHERE student_id = :studentId")
    fun updateStatus(studentId: String, status: String): Completable

    @Query("SELECT * FROM student")
    fun getAll(): Maybe<List<StudentEntity>>

    @Query("SELECT * FROM student WHERE class_id = :classId")
    fun getByClassId(classId: String): Maybe<List<StudentEntity>>
}
