package info.tduty.typetalk.view.teacher.classinfo

import info.tduty.typetalk.domain.interactor.StudentInteractor
import info.tduty.typetalk.domain.managers.EventManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
class ClassPresenter(
    private val view: ClassView,
    private val studentInteractor: StudentInteractor,
    private val eventManager: EventManager
) {

    private var disposable: Disposable? = null
    private val eventDisposable = CompositeDisposable()

    fun onCreate(classId: String) {
        listenEvents()
        getStudents(classId)
    }

    fun onDestroy() {
        disposable?.dispose()
        eventDisposable.dispose()
    }

    fun openClassChat() {
        view.openClassChat()
    }

    fun openChat(chatId: String) {
        view.openChat(chatId)
    }

    private fun listenEvents() {
        eventDisposable.add(
            eventManager.messageNew()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view.updateExistNewMessages(it.chatId, true) }, Timber::e)
        )

        eventDisposable.add(
            eventManager.cleanBadge()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view.updateExistNewMessages(it.chatId, false) }, Timber::e)
        )

        eventDisposable.add(
            eventManager.userStatusUpdated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view.updateStatus(it.studentId, it.status) }, Timber::e)
        )
    }

    private fun getStudents(classId: String) {
        disposable?.dispose()
        disposable = studentInteractor.getStudents(classId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ students ->
                view.setStudents(students)
            }, Timber::e)
    }
}
