package info.tduty.typetalk.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import info.tduty.typetalk.data.entity.*

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(accountEntity: AccountEntity): Long?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(lessonEntity: LessonEntity): Long

    @Query("SELECT * FROM chat ORDER BY id DESC")
    fun getAllAccounts(): List<AccountEntity?>?

    @Query("SELECT * FROM chat WHERE id =:id")
    fun getAccount(id: Long): AccountEntity?

    @Update
    fun update(accountEntity: AccountEntity)

    @Delete
    fun delete(accountEntity: AccountEntity?)

    @Transaction
    @Query("SELECT * FROM chat WHERE id = :accountId")
    fun loadAccountBy(accountId: String?): LiveData<AccountLessons?>?

    @Transaction
    fun insert(accountLessons: AccountLessons) {
        insert(accountLessons.account)
        for (lessons in accountLessons.lessons!!) {
            insert(lessons)
        }
    }
}