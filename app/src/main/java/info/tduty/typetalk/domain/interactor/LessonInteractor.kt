package info.tduty.typetalk.domain.interactor

import info.tduty.typetalk.R
import info.tduty.typetalk.data.db.model.LessonEntity
import info.tduty.typetalk.data.db.model.TaskEntity
import info.tduty.typetalk.data.db.wrapper.LessonWrapper
import info.tduty.typetalk.data.db.wrapper.TaskWrapper
import info.tduty.typetalk.data.dto.LessonDTO
import info.tduty.typetalk.data.event.payload.LessonPayload
import info.tduty.typetalk.data.event.payload.LessonProgressPayload
import info.tduty.typetalk.data.model.ExpectedVO
import info.tduty.typetalk.data.model.LessonVO
import info.tduty.typetalk.domain.managers.EventManager
import info.tduty.typetalk.domain.provider.LessonProvider
import info.tduty.typetalk.utils.toStringList
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
class LessonInteractor(
    private val lessonProvider: LessonProvider,
    private val lessonWrapper: LessonWrapper,
    private val taskWrapper: TaskWrapper,
    private val eventManager: EventManager
) {

    fun loadLessons(): Completable {
        return lessonProvider.getLessons()
            .flatMapCompletable { dtoList ->
                val dbList = dtoList.map { toDB(0, it) }
                val allTasks = dtoList.map { toTaskDB(it) }.flatten()
                lessonWrapper.insert(dbList)
                    .andThen(taskWrapper.insert(allTasks))
            }
    }

    fun getLessons(): Observable<List<LessonVO>> {
        return lessonWrapper.getLessons()
            .map { dbList ->
                dbList.mapIndexed { index, lesson -> toVO(index, lesson) }
            }
    }

    fun getLesson(id: String): Observable<LessonVO> {
        return lessonWrapper.getLesson(id)
            .map { toVO(0, it) }
            .switchIfEmpty(
                lessonProvider.getLesson(id)
                    .doOnError { Timber.e(it) }
                    .flatMap { dto ->
                        val db = toDB(0, dto)
                        lessonWrapper.insert(toDB(0, dto))
                            .andThen(Observable.just(db))
                            .map { toVO(0, it) }
                    }
            )
    }

    fun addLesson(lesson: LessonPayload): Completable {
        val db = toDB(0, lesson)
        return lessonWrapper.insert(db)
            .doOnComplete {
                eventManager.post(
                    toVO(
                        0,
                        db
                    )
                )
            } //TODO правильно проставлять номер урока
    }

    private fun toTaskDB(dto: LessonDTO): List<TaskEntity> {
        return dto.taskDTOList.map {
            TaskEntity(
                taskId = it.id,
                title = it.title,
                type = it.type,
                position = it.position,
                iconUrl = it.icon,
                isPerformed = it.status == 1,
                optional = it.optional,
                payload = it.payload,
                lessonsId = dto.id
            )
        }
    }

    private fun toDB(index: Int, dto: LessonDTO): LessonEntity {
        return LessonEntity(
            lessonId = dto.id,
            title = dto.title,
            lessonNumber = index + 1,
            description = dto.description,
            status = dto.status,
            expectedList = dto.expected.map { it.title }.toStringList()
        )
    }

    private fun toDB(index: Int, payload: LessonPayload): LessonEntity {
        return LessonEntity(
            lessonId = payload.id,
            title = payload.title,
            lessonNumber = index + 1,
            description = payload.description,
            status = payload.status,
            expectedList = (payload.expectedList?.map { it.title } ?: emptyList()).toStringList()
        )
    }

    private fun toVO(index: Int, db: LessonEntity): LessonVO {
        return LessonVO(
            db.lessonId,
            number = index + 1,
            title = db.title,
            content = db.description,
            icon = R.drawable.ic_cardiogram,
            status = LessonVO.getStatus(db.status),
            expectedList = listOf(
                ExpectedVO("Translate", R.drawable.ic_translation),
                ExpectedVO("Phrases", R.drawable.ic_phrase_building),
                ExpectedVO("Hurry up", R.drawable.ic_hurry_up)
            )
        )
    }

    fun updateState(lessonId: String, state: Int): Completable {
        return lessonWrapper.update(lessonId, state)
            .doOnComplete {
                eventManager.post(LessonProgressPayload(lessonId, state))
            }
    }
}
