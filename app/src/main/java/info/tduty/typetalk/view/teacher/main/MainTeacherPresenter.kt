package info.tduty.typetalk.view.teacher.main

import info.tduty.typetalk.data.model.ClassVO
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
    private var classes: List<ClassVO> = emptyList()

    fun onCreate() {
        getClasses()
    }

    fun onDestroy() {
        disposable?.dispose()
    }

    fun openClass(id: String) {
        view.openClassScreen(id, classes.firstOrNull { it.id == id }?.name ?: "")
    }

    private fun getClasses() {
        disposable?.dispose()
        disposable = classInteractor.getClasses()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ classes ->
                this.classes = classes
                view.setClasses(classes)
            }, Timber::e)
    }
}
