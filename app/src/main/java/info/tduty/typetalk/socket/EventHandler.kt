package info.tduty.typetalk.socket

import info.tduty.typetalk.domain.interactor.HistoryInteractor
import info.tduty.typetalk.domain.interactor.LessonInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 08.03.2020.
 */
class EventHandler(
    private val socketEventListener: SocketEventListener,
    private val historyInteractor: HistoryInteractor,
    private val lessonInteractor: LessonInteractor
) {

    private val disposables = CompositeDisposable()

    init {
        disposables.addAll(listenMessageNew())
        disposables.addAll(listenTyping())
        disposables.addAll(listenLessons())
    }

    private fun listenMessageNew(): Disposable {
        return socketEventListener.messageNewPayloadObservable()
            .flatMapCompletable { historyInteractor.addMessage(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({}, Timber::e)
    }

    private fun listenTyping(): Disposable {
        return socketEventListener.typingPayloadObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({}, Timber::e)
    }

    private fun listenLessons(): Disposable {
        return socketEventListener.lessonPayloadObservable()
            .flatMapCompletable { lessonInteractor.addLesson(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({}, Timber::e)
    }
}