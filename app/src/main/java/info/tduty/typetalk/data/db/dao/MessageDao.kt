package info.tduty.typetalk.data.db.dao

import androidx.room.*
import info.tduty.typetalk.data.db.model.MessageEntity
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(message: MessageEntity): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(messages: List<MessageEntity>): Completable

    @Query("SELECT * FROM message ORDER BY id DESC")
    fun getAllMessages(): Maybe<List<MessageEntity>>

    @Query("SELECT * FROM message WHERE id =:id")
    fun getMessage(id: Long): Maybe<MessageEntity>

    @Query("SELECT * FROM message WHERE chat_id =:chatId")
    fun getByChatId(chatId: String): Maybe<List<MessageEntity>>

    @Update
    fun update(message: MessageEntity)

    @Query("UPDATE message SET additional_type = :type, additional = :text WHERE sync_id = :syncId")
    fun updateAdditional(syncId: String, type: Int, text: String): Completable

    @Delete
    fun delete(message: MessageEntity)
}
