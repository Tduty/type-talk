package info.tduty.typetalk.db

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import info.tduty.typetalk.data.db.AppDatabase
import info.tduty.typetalk.data.db.DataBaseBuilderRoom
import info.tduty.typetalk.data.entity.MessageEntity
import info.tduty.typetalk.data.entity.TaskEntity
import junit.framework.Assert
import org.junit.After
import org.junit.Before
import org.junit.Test

class TaskDataTests {
    var appDataBase: AppDatabase? = null

    @Before
    fun init() {
        appDataBase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext, AppDatabase::class.java).build()
    }


    @Test
    fun test_addTaskData_resultTasEntityInDB() {
        var task =
            TaskEntity(
                0,
                "taskTitleMock",
                "descriptionMock",
                "http://iconUrlMock",
                true,
                null
            )
        val idTask = appDataBase?.getTaskDao()?.insert(task)
        task.id = idTask
        appDataBase?.getTaskDao()?.getTask(idTask)?.let { isEquals(task, it) }
            ?.let { Assert.assertTrue(it) }
    }

    private fun isEquals(taskMock: TaskEntity, taskDB: TaskEntity): Boolean {
        return taskMock.id == taskDB.id &&
                taskMock.title == taskDB.title &&
                taskMock.description == taskDB.description &&
                taskMock.iconUrl == taskDB.iconUrl &&
                taskMock.isPerformed == taskDB.isPerformed &&
                taskMock.lessonsId == taskDB.lessonsId
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        appDataBase?.close()
    }
}
