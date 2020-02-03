package info.tduty.typetalk.db

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import info.tduty.typetalk.data.db.AppDatabase
import info.tduty.typetalk.data.db.DataBaseBuilderRoom
import info.tduty.typetalk.data.entity.ChatEntity
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test


class ChatDataTests {

    var appDataBase: AppDatabase? = null

    @Before
    fun init() {
        appDataBase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext, AppDatabase::class.java).build()
    }


    @Test
    fun test_addChatData_resultChatEntityInDB() {
        var chat = ChatEntity(0, "mockChatTitle", "http://mockUrl","mockDescription")
        val idChat = appDataBase?.getChatDao()?.insert(chat)
        chat.id = idChat?.or(chat.id + 1)!!
        appDataBase?.getChatDao()?.getChat(idChat)?.let { isEquals(chat, it) }?.let { assertTrue(it) }
    }

    private fun isEquals(chatMock: ChatEntity, chatDB: ChatEntity) : Boolean {
        return chatMock.id == chatDB.id &&
                chatMock.title == chatDB.title &&
                chatMock.description == chatDB.description &&
                chatMock.imageURL == chatDB.imageURL
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        appDataBase?.close()
    }
}