package info.tduty.typetalk.view.main

import info.tduty.typetalk.domain.interactor.LessonInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 2019-11-20.
 */
class MainPresenter(
    private val view: MainView,
    private val lessonInteractor: LessonInteractor
) {

    private val disposables = CompositeDisposable()

    fun onCreate() {
        listenerAddLesson()
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

    private fun listenerAddLesson() {

    }

    private fun getLessons() {
        disposables.add(
            lessonInteractor.getLessons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view.setLessons(it) }, Timber::e)
        )
    }
}
