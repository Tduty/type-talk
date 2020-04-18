package info.tduty.typetalk.domain.interactor

import info.tduty.typetalk.data.db.model.StudentEntity
import info.tduty.typetalk.data.db.wrapper.ClassWrapper
import info.tduty.typetalk.data.db.wrapper.StudentWrapper
import info.tduty.typetalk.data.dto.StudentDTO
import info.tduty.typetalk.data.model.StudentVO
import info.tduty.typetalk.data.model.UserStatusUpdated
import info.tduty.typetalk.domain.managers.EventManager
import info.tduty.typetalk.domain.provider.StudentProvider
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
class StudentInteractor(
    private val studentWrapper: StudentWrapper,
    private val classWrapper: ClassWrapper,
    private val studentProvider: StudentProvider,
    private val eventManager: EventManager
) {

    fun getStudents(classId: String): Observable<List<StudentVO>> {
        return studentWrapper.getByClassId(classId)
            .map { students -> students.map { toVO(it) } }
    }

    fun loadStudents(): Completable {
        return classWrapper.getAll()
            .flatMap { Observable.fromIterable(it) }
            .flatMapCompletable { loadStudents(it.classId) }
    }

    fun updateStudentStatus(studentId: String, status: String): Completable {
        return studentWrapper.updateStatus(studentId, status)
            .doOnComplete { eventManager.post(UserStatusUpdated(studentId, StudentVO.Status.entityOf(status))) }
    }

    private fun loadStudents(classId: String): Completable {
        return studentProvider.getStudents(classId)
            .doOnError { Timber.e(it) }
            .flatMapCompletable { students ->
                studentWrapper.insert(students.map { toEntity(it) })
            }
    }

    private fun toEntity(dto: StudentDTO): StudentEntity {
        return StudentEntity(
            studentId = dto.id,
            chatId = dto.chatId,
            classId = dto.classId,
            name = dto.name,
            type = dto.type,
            status = dto.status
        )
    }

    private fun toVO(entity: StudentEntity): StudentVO {
        return StudentVO(
            entity.studentId,
            entity.chatId,
            entity.name,
            StudentVO.Type.entityOf(entity.type),
            StudentVO.Status.entityOf(entity.status)
        )
    }
}