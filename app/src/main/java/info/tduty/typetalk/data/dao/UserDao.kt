package info.tduty.typetalk.data.dao

import androidx.room.*
import info.tduty.typetalk.data.entity.MessageEntity
import info.tduty.typetalk.data.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    fun insert(message: UserEntity?): Long?

    @Query("SELECT * FROM user ORDER BY id DESC")
    fun getAllMessages(): List<UserEntity?>?

    @Query("SELECT * FROM user WHERE id =:id")
    fun getMessage(id: Int): UserEntity?

    @Update
    fun update(user: UserEntity?)

    @Delete
    fun delete(user: UserEntity?)
}