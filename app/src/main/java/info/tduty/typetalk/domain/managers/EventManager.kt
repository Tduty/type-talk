package info.tduty.typetalk.domain.managers

import info.tduty.typetalk.data.model.CleanBadge
import info.tduty.typetalk.data.model.LessonVO
import info.tduty.typetalk.data.model.MessageVO
import info.tduty.typetalk.data.model.UserStatusUpdated
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Evgeniy Mezentsev on 08.03.2020.
 */
class EventManager {

    private var subject: PublishSubject<Any> = PublishSubject.create()

    fun post(any: Any) {
        subject.onNext(any)
    }

    fun messageNew(): Flowable<MessageVO> {
        return subject
            .toFlowable(BackpressureStrategy.BUFFER)
            .filter { it is MessageVO }
            .cast(MessageVO::class.java)
    }

    fun lessonNew(): Flowable<LessonVO> {
        return subject
            .toFlowable(BackpressureStrategy.BUFFER)
            .filter { it is LessonVO }
            .cast(LessonVO::class.java)
    }

    fun cleanBadge(): Flowable<CleanBadge> {
        return subject
            .toFlowable(BackpressureStrategy.BUFFER)
            .filter { it is CleanBadge }
            .cast(CleanBadge::class.java)
    }

    fun userStatusUpdated(): Flowable<UserStatusUpdated> {
        return subject
            .toFlowable(BackpressureStrategy.BUFFER)
            .filter { it is UserStatusUpdated }
            .cast(UserStatusUpdated::class.java)
    }
}