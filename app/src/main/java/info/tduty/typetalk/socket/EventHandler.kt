package info.tduty.typetalk.socket

import info.tduty.typetalk.domain.interactor.HistoryInteractor
import info.tduty.typetalk.domain.interactor.LessonInteractor
import info.tduty.typetalk.domain.interactor.StudentInteractor
import info.tduty.typetalk.domain.interactor.TaskInteractor
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
    private val lessonInteractor: LessonInteractor,
    private val studentInteractor: StudentInteractor,
    private val taskInteractor: TaskInteractor
) {

    private val disposables = CompositeDisposable()

    init {
        disposables.addAll(listenMessageNew())
        disposables.addAll(listenTyping())
        disposables.addAll(listenLessons())
        disposables.addAll(listenUserStatuses())
        disposables.addAll(listenCorrection())
        disposables.addAll(listenTask())
        disposables.addAll(listenLessonState())
    }

    private fun listenMessageNew(): Disposable {
        return socketEventListener.messageNewPayloadObservable()
            .flatMapCompletable { historyInteractor.addMessage(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({}, Timber::e)
    }

    private fun listenUserStatuses(): Disposable {
        return socketEventListener.userStatusPayloadObservable()
            .flatMapCompletable { studentInteractor.updateStudentStatus(it.userId, it.status) }
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

    private fun listenCorrection(): Disposable {
        return socketEventListener.correctionPayloadObservable()
            .flatMapCompletable { historyInteractor.handleCorrection(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({}, Timber::e)
    }

    private fun listenTask(): Disposable {
        return socketEventListener.completedTaskPayloadObservable()
            .flatMapCompletable { taskInteractor.updateTaskState(it.lessonId, it.taskId, it.isCompleted) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({}, Timber::e)
    }

    private fun listenLessonState(): Disposable {
        return socketEventListener.lessonProgressPayloadObservable()
            .flatMapCompletable { lessonInteractor.updateState(it.lessonId, it.state ?: 0) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({}, Timber::e)
    }
}