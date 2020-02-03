package info.tduty.typetalk.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import info.tduty.typetalk.data.entity.LessonEntity


@Dao
interface LessonDao {

    @Query("SELECT * FROM lessons ORDER BY id DESC")
    fun getAllLessons(): List<LessonEntity?>?

    @Query("SELECT * FROM lessons WHERE id =:id")
    fun getLesson(id: Long?): LessonEntity?

    @Update
    fun update(lessons: LessonEntity?)

    @Delete
    fun delete(lessons: LessonEntity?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(lesson: LessonEntity?): Long

    @Transaction
    @Query("SELECT * FROM lessons WHERE id = :lessonsId")
    fun loadLessonsBy(lessonsId: String?): LiveData<LessonEntity?>?

    @Transaction
    @Query("SELECT * FROM lessons WHERE id = :lessonsId")
    fun getLessonsBy(lessonsId: String?): LessonEntity?
}
