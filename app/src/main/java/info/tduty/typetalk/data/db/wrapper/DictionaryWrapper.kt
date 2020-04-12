package info.tduty.typetalk.data.db.wrapper

import info.tduty.typetalk.data.db.dao.DictionaryDao
import info.tduty.typetalk.data.db.model.DictionaryEntity
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 09.04.2020.
 */
class DictionaryWrapper(private val dictionaryDao: DictionaryDao) {

    fun insert(dictionary: DictionaryEntity): Completable {
        return dictionaryDao.insert(dictionary)
    }

    fun insert(dictionary: List<DictionaryEntity>): Completable {
        return dictionaryDao.insertList(dictionary)
    }

    fun getAll(): Observable<List<DictionaryEntity>> {
        return dictionaryDao.getAllDictionary()
            .toObservable()
    }

    fun getByLessonId(lessonId: String): Observable<DictionaryEntity> {
        return dictionaryDao.getDictionaryByLessonId(lessonId)
            .toObservable()
    }
}