package info.tduty.typetalk.data.dao

import androidx.room.*

@Dao
interface MessageDao {
    @Insert
    fun insert(u: MessageDao?): Long?

    @Query("SELECT * FROM message ORDER BY id DESC")
    fun getAllMessages(): List<MessageDao?>?

    @Query("SELECT * FROM message WHERE id =:id")
    fun getMessage(id: Int): MessageDao?

    @Update
    fun update(message: MessageDao?)

    @Delete
    fun delete(message: MessageDao?)
}