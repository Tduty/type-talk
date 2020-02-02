package info.tduty.typetalk.db

import androidx.test.platform.app.InstrumentationRegistry
import info.tduty.typetalk.data.db.AppDatabase
import info.tduty.typetalk.data.db.DataBaseBuilderRoom
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test


class ChatDataTests {

    var appDataBase: AppDatabase? = null

    @Before
    fun init() {
        appDataBase = DataBaseBuilderRoom(InstrumentationRegistry.getInstrumentation().targetContext).build()
    }


    @After
    @Throws(Exception::class)
    fun closeDb() {
        appDataBase?.close()
    }

    @Test
    fun testMock2() {
        assert(true)
    }
}