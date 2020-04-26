package info.tduty.typetalk.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import info.tduty.typetalk.data.db.model.LessonEntity
import io.reactivex.Completable
import io.reactivex.Maybe


@Dao
interface LessonDao {

    @Query("SELECT * FROM lessons")
    fun getAllLessons(): Maybe<List<LessonEntity>>

    @Query("SELECT * FROM lessons WHERE lesson_id IN (:lessonsIds)")
    fun getLessons(lessonsIds: List<String>): Maybe<List<LessonEntity>>

    @Query("SELECT * FROM lessons WHERE lesson_id =:id")
    fun getLesson(id: String): Maybe<LessonEntity>

    @Update
    fun update(lessons: LessonEntity): Completable

    @Delete
    fun delete(lessons: LessonEntity?): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(lesson: LessonEntity): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(lessons: List<LessonEntity>): Completable

    @Transaction
    @Query("SELECT * FROM lessons WHERE id = :lessonsId")
    fun loadLessonsBy(lessonsId: String?): LiveData<LessonEntity?>?

    @Transaction
    @Query("SELECT * FROM lessons WHERE id = :lessonsId")
    fun getLessonsBy(lessonsId: String?): Maybe<LessonEntity>

    @Query("UPDATE lessons SET status = :state WHERE lesson_id = :lessonId")
    fun updateState(lessonId: String, state: Int): Completable
}
