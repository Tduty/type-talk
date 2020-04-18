package info.tduty.typetalk.domain.interactor

import info.tduty.typetalk.data.db.model.ClassEntity
import info.tduty.typetalk.data.db.wrapper.ClassWrapper
import info.tduty.typetalk.data.dto.ClassDTO
import info.tduty.typetalk.data.model.ClassVO
import info.tduty.typetalk.domain.provider.ClassProvider
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
class ClassInteractor(
    private val classWrapper: ClassWrapper,
    private val classProvider: ClassProvider
) {

    fun loadClasses(): Completable {
        return classProvider.getClasses()
            .flatMapCompletable { classes ->
                classWrapper.insert(classes.map { toClassEntity(it) })
            }
    }

    fun getClasses(): Observable<List<ClassVO>> {
        return classWrapper.getAll()
            .map { classes -> classes.map { toClassVO(it) } }
    }

    private fun toClassEntity(classDTO: ClassDTO): ClassEntity {
        return ClassEntity(
            classId = classDTO.id,
            title = classDTO.title,
            description = classDTO.description,
            memberCount = classDTO.memberCount
        )
    }

    private fun toClassVO(classEntity: ClassEntity): ClassVO {
        return ClassVO(
            id = classEntity.classId,
            name = classEntity.title,
            countMembers = classEntity.memberCount
        )
    }
}
