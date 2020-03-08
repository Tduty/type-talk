package info.tduty.typetalk.domain.interactor

import info.tduty.typetalk.R
import info.tduty.typetalk.data.db.model.LessonEntity
import info.tduty.typetalk.data.db.wrapper.LessonWrapper
import info.tduty.typetalk.data.dto.LessonDTO
import info.tduty.typetalk.data.event.payload.LessonPayload
import info.tduty.typetalk.data.model.ExpectedVO
import info.tduty.typetalk.data.model.LessonVO
import info.tduty.typetalk.data.model.StatusVO
import info.tduty.typetalk.domain.managers.EventManager
import info.tduty.typetalk.domain.provider.LessonProvider
import info.tduty.typetalk.utils.toStringList
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
class LessonInteractor(
    private val lessonProvider: LessonProvider,
    private val lessonWrapper: LessonWrapper,
    private val eventManager: EventManager
) {

    fun getLessons(): Observable<List<LessonVO>> {
        return lessonProvider.getLessons()
            .flatMap { dtoList ->
                val dbList = dtoList.map { toDB(it) }
                val voList = dbList.mapIndexed { index, lesson ->
                    toVO(index, lesson)
                }
                lessonWrapper.insert(dbList)
                    .andThen(Observable.just(voList))
            }
    }

    fun addLesson(lesson: LessonPayload): Completable {
        val db = toDB(lesson)
        return lessonWrapper.insert(db)
            .doOnComplete { eventManager.post(toVO(0, db)) } //TODO правильно проставлять номер урока
    }

    private fun toDB(dto: LessonDTO): LessonEntity {
        return LessonEntity(
            lessonId = dto.id,
            title = dto.title,
            description = dto.description,
            status = dto.status,
            expectedList = dto.expected.map { it.title }.toStringList()
        )
    }

    private fun toDB(payload: LessonPayload): LessonEntity {
        return LessonEntity(
            lessonId = payload.id,
            title = payload.title,
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
            icon = R.drawable.ic_teacher_bg_corn,
            status = StatusVO(R.drawable.ic_checkbox_empty, "Awaible"), //TODO: переписать на нормальный вариант
            expectedList = db.expectedList.strings
                .map { ExpectedVO("Test", R.drawable.ic_lesson_expected_dialoges) }
        )
    }
}