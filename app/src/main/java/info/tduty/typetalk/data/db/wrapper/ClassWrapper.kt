package info.tduty.typetalk.data.db.wrapper

import info.tduty.typetalk.data.db.dao.ClassDao
import info.tduty.typetalk.data.db.model.ClassEntity
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
class ClassWrapper(private val classDao: ClassDao) {

    fun insert(classEntity: ClassEntity): Completable {
        return classDao.insert(classEntity)
    }

    fun insert(classes: List<ClassEntity>): Completable {
        return classDao.insertList(classes)
    }

    fun getAll(): Observable<List<ClassEntity>> {
        return classDao.getAll()
            .toObservable()
    }
}
