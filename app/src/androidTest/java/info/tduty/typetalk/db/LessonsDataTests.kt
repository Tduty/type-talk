package info.tduty.typetalk.db

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import info.tduty.typetalk.data.db.AppDatabase
import info.tduty.typetalk.data.entity.LessonEntity
import junit.framework.Assert
import org.junit.After
import org.junit.Before
import org.junit.Test

class LessonsDataTests {
    var appDataBase: AppDatabase? = null

    @Before
    fun init() {
        appDataBase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        ).build()
    }


    @Test
    fun test_addLessonData_resultLessonEntityInDB() {
        var lesson =
            LessonEntity(
                0,
                "lessonsTitleMock",
                "descriptionMock",
                false
            )
        val idLesson = appDataBase?.getLessonsDao()?.insert(lesson)
        lesson.id = lesson.id
        appDataBase?.getLessonsDao()?.getLesson(idLesson)?.let { isEquals(lesson, it) }
            ?.let { Assert.assertTrue(it) }
    }

    private fun isEquals(lessonMock: LessonEntity, lessonDB: LessonEntity): Boolean {
        return lessonMock.id == lessonDB.id &&
                lessonMock.title == lessonDB.title &&
                lessonMock.description == lessonDB.description &&
                lessonMock.isPerformed == lessonDB.isPerformed
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        appDataBase?.close()
    }
}
