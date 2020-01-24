package info.tduty.typetalk.db

import android.accounts.Account
import androidx.test.platform.app.InstrumentationRegistry
import info.tduty.typetalk.data.db.AppDatabase
import info.tduty.typetalk.data.db.DataBaseBuilderRoom
import info.tduty.typetalk.data.entity.AccountEntity
import info.tduty.typetalk.data.entity.Role
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class AccountDataTests {

    var appDataBase: AppDatabase? = null

    @Before
    fun init() {
        appDataBase = DataBaseBuilderRoom(InstrumentationRegistry.getInstrumentation().targetContext).build()
    }

    @Test
    fun test_insertNewAccount_resultSaveNewAccountInDB() {
        var accountEntity = AccountEntity(0, "loginMock", "password", null, Role.ADMIN)
        var id = appDataBase?.getAccountDao()?.insert(accountEntity)
        assertEquals(appDataBase?.getAccountDao()?.getAccount(id!!), accountEntity)
    }

    @Test
    fun testMock2() {
        assert(true)
    }
}