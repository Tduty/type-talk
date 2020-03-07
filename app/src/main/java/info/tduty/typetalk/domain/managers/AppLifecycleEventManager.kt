package info.tduty.typetalk.domain.managers

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
class AppLifecycleEventManager : LifecycleObserver {
    private val subject: PublishSubject<Lifecycle.Event> = PublishSubject.create()
    private val flowable: Flowable<Lifecycle.Event> = subject.toFlowable(BackpressureStrategy.BUFFER)

    private var foreground: Boolean = false

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    internal fun onCreate() {
        handleAppLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    internal fun onStart() {
        handleAppLifecycleEvent(Lifecycle.Event.ON_START)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    internal fun onResume() {
        handleAppLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    internal fun onPause() {
        handleAppLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    internal fun onStop() {
        handleAppLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    internal fun onDestroy() {
        handleAppLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    fun isForeground(): Boolean {
        return foreground
    }

    fun onAppCreate(): Flowable<Lifecycle.Event> {
        return flowable
            .filter { it == Lifecycle.Event.ON_CREATE }
    }

    fun onAppStart(): Flowable<Lifecycle.Event> {
        return flowable
            .filter { it == Lifecycle.Event.ON_START }
    }

    fun onAppResume(): Flowable<Lifecycle.Event> {
        return flowable
            .filter { it == Lifecycle.Event.ON_RESUME }
    }

    fun onAppPause(): Flowable<Lifecycle.Event> {
        return flowable
            .filter { it == Lifecycle.Event.ON_PAUSE }
    }

    fun onAppStop(): Flowable<Lifecycle.Event> {
        return flowable
            .filter { it == Lifecycle.Event.ON_STOP }
    }

    fun onAppDestroy(): Flowable<Lifecycle.Event> {
        return flowable
            .filter { it == Lifecycle.Event.ON_DESTROY }
    }

    private fun handleAppLifecycleEvent(event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_START) {
            foreground = true
        } else if (event == Lifecycle.Event.ON_STOP) {
            foreground = false
        }
        subject.onNext(event)
    }
}