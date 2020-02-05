package info.tduty.typetalk.db

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import info.tduty.typetalk.data.db.AppDatabase
import info.tduty.typetalk.data.entity.DictionaryEntity
import junit.framework.Assert
import org.junit.After
import org.junit.Before
import org.junit.Test

class DictionaryDataTests {
    var appDataBase: AppDatabase? = null

    @Before
    fun init() {
        appDataBase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext, AppDatabase::class.java).build()
    }


    @Test
    fun test_addDictionaryData_resultDictionaryEntityInDB() {
        var dictionary =
            DictionaryEntity(0,
                "wordMock",
                "translationMock",
                "transcriptionMock",
                null)
        val idDictionary = appDataBase?.getDictionaryDao()?.insert(dictionary)
        dictionary.id = dictionary.id
        appDataBase?.getDictionaryDao()?.getDictionary(idDictionary)?.let { isEquals(dictionary, it) }?.let { Assert.assertTrue(it) }
    }

    private fun isEquals(dictionaryMock: DictionaryEntity,dictionaryDB: DictionaryEntity): Boolean {
        return dictionaryMock.id == dictionaryDB.id &&
                dictionaryMock.word == dictionaryDB.word &&
                dictionaryMock.translation == dictionaryDB.translation &&
                dictionaryMock.transcription == dictionaryDB.transcription &&
                dictionaryMock.lessonsId == dictionaryDB.lessonsId
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        appDataBase?.close()
    }
}
