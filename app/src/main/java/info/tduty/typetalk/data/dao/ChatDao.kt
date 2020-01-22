package info.tduty.typetalk.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import info.tduty.typetalk.data.entity.ChatEntity

@Dao
interface ChatDao {

    @Insert
    fun insert(u: ChatEntity?): Long?

    @Query("SELECT * FROM chat ORDER BY id DESC")
    fun getAllChats(): List<ChatEntity?>?

    @Query("SELECT * FROM chat WHERE id =:id")
    fun getChat(id: Int): ChatEntity?

    @Update
    fun update(chat: ChatEntity?)

    @Delete
    fun delete(chat: ChatEntity?)

    @Transaction
    @Query("SELECT * FROM chat WHERE id = :chatId")
    fun loadChatBy(chatId: String?): LiveData<ChatEntity?>?

    @Transaction
    @Query("SELECT * FROM chat WHERE id = :chatId")
    fun getChatBy(chatId: String?): ChatEntity?
}