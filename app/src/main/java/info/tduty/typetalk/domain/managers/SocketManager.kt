package info.tduty.typetalk.domain.managers

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Evgeniy Mezentsev on 06.04.2020.
 */
class SocketManager {

    private var subject: PublishSubject<SocketState> = PublishSubject.create()

    fun post(state: SocketState) {
        subject.onNext(state)
    }

    fun onOpened(): Flowable<Boolean> {
        return subject
            .toFlowable(BackpressureStrategy.BUFFER)
            .filter { it == SocketState.OPEN }
            .map { true }
    }

    fun onClosed(): Flowable<Boolean> {
        return subject
            .toFlowable(BackpressureStrategy.BUFFER)
            .filter { it == SocketState.CLOSE }
            .map { true }
    }
}

enum class SocketState {
    OPEN,
    CLOSE
}
