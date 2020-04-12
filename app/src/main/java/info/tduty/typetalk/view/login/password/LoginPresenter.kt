package info.tduty.typetalk.view.login.password

import info.tduty.typetalk.domain.interactor.LoginInteractor
import info.tduty.typetalk.domain.managers.DataLoaderManager
import info.tduty.typetalk.socket.SocketController
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 12.03.2020.
 */
class LoginPresenter(private val view: LoginView,
                     private val dataLoaderManager: DataLoaderManager,
                     private val socketController: SocketController,
                     private val loginInteractor: LoginInteractor) {

    private val disposables = CompositeDisposable()

    fun onCreate() {

    }

    fun onDestroy() {
        disposables.dispose()
    }

    fun onNext(login: String, password: String) {
        val disposable = loginInteractor.auth(login, password)
            .andThen(Completable.defer { dataLoaderManager.loadData() })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showProgress() }
            .doFinally { view.hideProgress() }
            .subscribe({
                view.openMainScreen()
                socketController.connect()
            }, {
                view.setClickableBtn(true)
                view.showError()
                Timber.e(it)
            })
        disposables.add(disposable)
    }
}
