package info.tduty.typetalk.socket

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 04.03.2020.
 */
class EventBusRx {

    private val subject: Subject<Any> = PublishSubject.create()

    fun post(event: Any) {
        subject.onNext(event)
    }

    fun observeEvents(): Flowable<Any> = subject.toFlowable(BackpressureStrategy.BUFFER)

    fun <T> observeEvents(classType: Class<T>): Flowable<T> =
        subject
            .doOnNext { Timber.d("tesrt") }
            .toFlowable(BackpressureStrategy.BUFFER)
            .ofType(classType)
}
