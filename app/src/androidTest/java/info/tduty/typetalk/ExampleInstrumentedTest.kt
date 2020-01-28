package info.tduty.typetalk

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import info.tduty.typetalk.data.db.AppDatabase
import info.tduty.typetalk.data.db.DataBaseBuilderRoom
import info.tduty.typetalk.data.entity.AccountEntity
import info.tduty.typetalk.data.entity.Role
import junit.framework.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.After
import androidx.room.Room


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("info.tduty.typetalk", appContext.packageName)
    }

    var appDataBase: AppDatabase? = null

    @Before
    fun init() {
        appDataBase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        ).build()
    }

    @Test
    fun test_insertNewAccount_resultSaveNewAccountInDB() {
        var accountEntity = AccountEntity(0, "loginMock", "password", null, Role.ADMIN)
        var id = appDataBase?.getAccountDao()?.insert(accountEntity)
        Assert.assertEquals(appDataBase?.getAccountDao()?.getAccount(id!!), accountEntity)
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
