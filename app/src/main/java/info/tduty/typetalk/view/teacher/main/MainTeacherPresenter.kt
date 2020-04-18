package info.tduty.typetalk.view.teacher.main

import info.tduty.typetalk.domain.interactor.ClassInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
class MainTeacherPresenter(
    private val view: MainTeacherView,
    private val classInteractor: ClassInteractor
) {

    private var disposable: Disposable? = null

    fun onCreate() {
        getClasses()
    }

    fun onDestroy() {
        disposable?.dispose()
    }

    fun openClass(id: String) {
        view.openClassScreen(id)
    }

    private fun getClasses() {
        disposable?.dispose()
        disposable = classInteractor.getClasses()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ view.setClasses(it) }, Timber::e)
    }
}
