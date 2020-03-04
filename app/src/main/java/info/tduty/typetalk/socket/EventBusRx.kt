package info.tduty.typetalk.socket

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * Created by Evgeniy Mezentsev on 04.03.2020.
 */
class EventBusRx {

    private val subject: Subject<Any> = PublishSubject.create()

    fun post(event: Any) {
        subject.onNext(event)
    }

    fun observeEvents(): Observable<Any> = subject

    fun <T> observeEvents(classType: Class<T>): Observable<T> = subject.ofType(classType)
}
