package info.tduty.typetalk.db

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import info.tduty.typetalk.data.db.AppDatabase
import info.tduty.typetalk.data.db.DataBaseBuilderRoom
import info.tduty.typetalk.data.entity.MessageEntity
import junit.framework.Assert
import org.junit.After
import org.junit.Before
import org.junit.Test

class MessageDataTests {

    var appDataBase: AppDatabase? = null

    @Before
    fun init() {
        appDataBase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext, AppDatabase::class.java).build()
    }


    @Test
    fun test_addMessageData_resultMessageEntityInDB() {
        var message =
            MessageEntity(0, "messageTitleMock", "contentMock", "http://avatarUrlMock", true, null)
        val idMessage = appDataBase?.getMessageDao()?.insert(message)
        message.id = idMessage?.or(message.id + 1)!!
        appDataBase?.getMessageDao()?.getMessage(idMessage)?.let { isEquals(message, it) }?.let { Assert.assertTrue(it) }
    }

    private fun isEquals(messageMock: MessageEntity, messageDB: MessageEntity): Boolean {
        return messageMock.id == messageDB.id &&
                messageMock.title == messageDB.title &&
                messageMock.avatarURL == messageDB.avatarURL &&
                messageMock.content == messageDB.content &&
                messageMock.isMy == messageDB.isMy &&
                messageMock.chatId == messageDB.chatId
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        appDataBase?.close()
    }
}