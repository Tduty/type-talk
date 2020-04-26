package info.tduty.typetalk.view.main

import info.tduty.typetalk.data.model.LessonVO
import info.tduty.typetalk.domain.interactor.LessonInteractor
import info.tduty.typetalk.domain.managers.DatabaseManager
import info.tduty.typetalk.domain.managers.EventManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 2019-11-20.
 */
class MainPresenter(
    private val view: MainView,
    private val lessonInteractor: LessonInteractor,
    private val eventManager: EventManager,
    private val databaseManager: DatabaseManager
) {

    private var lessons: List<LessonVO>? = null
    private val disposables = CompositeDisposable()

    fun onCreate() {
        listenerUpdatedLesson()
        listenerAddLesson()
        listenerProgressUpdatedLesson()
        getLessons()
    }

    fun onDestroy() {
        disposables.dispose()
    }

    fun openTeacherChat() {
        view.openTeacherChat()
    }

    fun openClassChat() {
        view.openClassChat()
    }

    fun openBots() {
        view.openBots()
    }

    fun openLesson(lessonId: String) {
        view.openLesson(lessonId)
    }

    private fun listenerUpdatedLesson() {
        disposables.add(
            databaseManager.lessonUpdated()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ getLessons() }, Timber::e)
        )
    }

    private fun listenerProgressUpdatedLesson() {
        disposables.add(
            eventManager.lessonProgressUpated()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ getLessons() }, Timber::e)
        )
    }

    private fun listenerAddLesson() {
        disposables.add(
            eventManager.lessonNew()
                .toObservable()
                .flatMap { lessonInteractor.getLesson(it.id) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.addLesson(it)
                }, Timber::e)
        )
    }

    private fun getLessons() {
        disposables.add(
            lessonInteractor.getLessons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ lessons ->
                    this.lessons = lessons
                    view.setLessons(lessons)
                }, Timber::e)
        )
    }
}
