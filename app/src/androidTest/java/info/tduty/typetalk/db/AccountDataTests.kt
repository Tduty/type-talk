package info.tduty.typetalk.db

import android.accounts.Account
import android.icu.text.IDNA
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import info.tduty.typetalk.data.db.AppDatabase
import info.tduty.typetalk.data.db.DataBaseBuilderRoom
import info.tduty.typetalk.data.entity.AccountEntity
import info.tduty.typetalk.data.entity.Role
import info.tduty.typetalk.data.entity.UserEntity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.After
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
        accountEntity.id = id?.or(0)!!
        assertEquals(appDataBase?.getAccountDao()?.getAccount(id!!), accountEntity)
    }

    @Test
    fun test_insertNewAccountWithUser_resultSaveNewAccountWithUserInDB() {
        var user = UserEntity(0, "mockName", "mockSurname", 0, null)
        var accountEntity = AccountEntity(0, "loginMock", "password", user, Role.ADMIN)
        var id = appDataBase?.getAccountDao()?.insert(accountEntity)
        accountEntity.id = id?.or(0)!!
        assertEquals(appDataBase?.getAccountDao()?.getAccount(id)?.value?.user, user)
    }

    @Test
    fun test_insertNewAccountWithUser_resultAccountObjectInDB() {
        var user = UserEntity(0, "mockName", "mockSurname", 0, null)
        var accountEntity = AccountEntity(0, "loginMock", "password", user, Role.ADMIN)
        var id = appDataBase?.getAccountDao()?.insert(accountEntity)
        val accountObject = appDataBase?.getAccountDao()?.getAccount(id)?.value
        assertTrue(accountObject != null)
        assertTrue(accountObject?.user != null)
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