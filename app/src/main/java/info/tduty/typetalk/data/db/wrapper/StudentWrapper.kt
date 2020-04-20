package info.tduty.typetalk.data.db.wrapper

import info.tduty.typetalk.data.db.dao.StudentDao
import info.tduty.typetalk.data.db.model.StudentEntity
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 19.04.2020.
 */
class StudentWrapper(private val studentDao: StudentDao) {

    fun insert(student: StudentEntity): Completable {
        return studentDao.insert(student)
    }

    fun insert(students: List<StudentEntity>): Completable {
        return studentDao.insertList(students)
    }

    fun updateStatus(studentId: String, status: String): Completable {
        return studentDao.updateStatus(studentId, status)
    }

    fun getByClassId(classId: String): Observable<List<StudentEntity>> {
        return studentDao.getByClassId(classId)
            .toObservable()
    }

    fun getAll(): Observable<List<StudentEntity>> {
        return studentDao.getAll()
            .toObservable()
    }
}