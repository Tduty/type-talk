package info.tduty.typetalk.domain.managers

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Evgeniy Mezentsev on 09.04.2020.
 */
class DatabaseManager {

    private var subject: PublishSubject<EventType> = PublishSubject.create()

    private fun post(eventType: EventType) {
        subject.onNext(eventType)
    }

    fun postLessonUpdated() {
        post(EventType.LESSON_UPDATED)
    }

    fun lessonUpdated(): Flowable<EventType> {
        return subject
            .toFlowable(BackpressureStrategy.BUFFER)
            .filter { it == EventType.LESSON_UPDATED }
    }

    enum class EventType {
        LESSON_UPDATED
    }
}