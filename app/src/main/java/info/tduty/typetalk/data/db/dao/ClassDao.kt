package info.tduty.typetalk.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.tduty.typetalk.data.db.model.ClassEntity
import io.reactivex.Completable
import io.reactivex.Maybe

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
@Dao
interface ClassDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(u: ClassEntity): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertList(u: List<ClassEntity>): Completable

    @Query("SELECT * FROM class")
    fun getAll(): Maybe<List<ClassEntity>>
}