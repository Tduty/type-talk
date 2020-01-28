package info.tduty.typetalk.data.dao

import androidx.room.*
import info.tduty.typetalk.data.entity.DictionaryEntity
import info.tduty.typetalk.data.entity.LessonEntity

@Dao
interface DictionaryDao {
    @Insert
    fun insert(u: DictionaryEntity?): Long?

    @Query("SELECT * FROM dictionary ORDER BY id DESC")
    fun getAllDictionary(): List<DictionaryEntity?>?

    @Query("SELECT * FROM dictionary WHERE id =:id")
    fun getDictionary(id: Int): DictionaryEntity?

    @Update
    fun update(dictionary: DictionaryEntity?)

    @Delete
    fun delete(dictionary: DictionaryEntity?)
}