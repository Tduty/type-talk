package info.tduty.typetalk.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import info.tduty.typetalk.data.dao.*
import info.tduty.typetalk.data.entity.*

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */

@Database(
    entities = [
        AccountEntity::class,
        UserEntity::class,
        ChatEntity::class,
        LessonEntity::class,
        MessageEntity::class,
        DictionaryEntity::class,
        GradeEntity::class
    ],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getAccountDao(): AccountDao
    abstract fun getChatDao(): ChatDao
    abstract fun getUserDao(): UserDao
    abstract fun getLessonsDao(): LessonDao
    abstract fun getMessageDao(): MessageDao
    abstract fun getDictionaryDao(): DictionaryDao
}