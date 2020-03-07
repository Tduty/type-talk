package info.tduty.typetalk.data.db.dao

import androidx.room.*
import info.tduty.typetalk.data.db.model.MessageEntity

@Dao
interface MessageDao {

    @Insert
    fun insert(message: MessageEntity?): Long?

    @Query("SELECT * FROM message ORDER BY id DESC")
    fun getAllMessages(): List<MessageEntity?>?

    @Query("SELECT * FROM message WHERE id =:id")
    fun getMessage(id: Long?): MessageEntity?

    @Update
    fun update(message: MessageEntity?)

    @Delete
    fun delete(message: MessageEntity?)
}
