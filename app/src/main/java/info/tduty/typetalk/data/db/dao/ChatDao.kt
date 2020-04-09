package info.tduty.typetalk.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import info.tduty.typetalk.data.db.model.ChatEntity
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(u: ChatEntity): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertList(u: List<ChatEntity>): Completable

    @Query("SELECT * FROM chat ORDER BY id DESC")
    fun getAllChats(): Maybe<List<ChatEntity>>

    @Query("SELECT * FROM chat WHERE id =:id")
    fun getChat(id: Long): Maybe<ChatEntity>

    @Update
    fun update(chat: ChatEntity): Completable

    @Delete
    fun delete(chat: ChatEntity): Completable

    @Transaction
    @Query("SELECT * FROM chat WHERE id = :chatId")
    fun loadChatBy(chatId: String?): LiveData<ChatEntity?>?

    @Transaction
    @Query("SELECT * FROM chat WHERE chat_id = :chatId")
    fun getChatBy(chatId: String): Maybe<ChatEntity>

    @Transaction
    @Query("SELECT * FROM chat WHERE type = :type")
    fun getChatByType(type: String): Maybe<ChatEntity>
}
