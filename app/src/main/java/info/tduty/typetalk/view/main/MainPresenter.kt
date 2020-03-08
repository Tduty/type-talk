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
        view.openChat("teacher") //TODO
    }

    fun openClassChat() {
        view.openChat("class") //TODO
    }

    fun openBotChat() {
        view.openChat("lesson") //TODO
    }

    fun openLesson(lessonId: String) {

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
