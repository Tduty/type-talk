package info.tduty.typetalk.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import info.tduty.typetalk.data.db.dao.*
import info.tduty.typetalk.data.db.model.*

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */

@Database(
    entities = [
        ClassEntity::class,
        StudentEntity::class,
        ChatEntity::class,
        LessonEntity::class,
        MessageEntity::class,
        DictionaryEntity::class,
        TaskEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DbConvector::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getClassDao(): ClassDao
    abstract fun getStudentDao(): StudentDao
    abstract fun getTaskDao(): TaskDao
    abstract fun getChatDao(): ChatDao
    abstract fun getLessonsDao(): LessonDao
    abstract fun getMessageDao(): MessageDao
    abstract fun getDictionaryDao(): DictionaryDao
}
