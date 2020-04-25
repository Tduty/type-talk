package info.tduty.typetalk.data.db.dao

import androidx.room.*
import info.tduty.typetalk.data.db.model.DictionaryEntity
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface DictionaryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(u: DictionaryEntity): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertList(u: List<DictionaryEntity>): Completable

    @Query("SELECT * FROM dictionary ORDER BY id DESC")
    fun getAllDictionary(): Maybe<List<DictionaryEntity>>

    @Query("SELECT * FROM dictionary WHERE id =:id")
    fun getDictionary(id: Long): Maybe<DictionaryEntity>

    @Query("SELECT * FROM dictionary WHERE lesson_id =:lessonId")
    fun getDictionaryByLessonId(lessonId: String): Maybe<DictionaryEntity>

    @Update
    fun update(dictionary: DictionaryEntity): Completable

    @Delete
    fun delete(dictionary: DictionaryEntity): Completable
}
