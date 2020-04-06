package info.tduty.typetalk.view.login.password

import info.tduty.typetalk.domain.interactor.LoginInteractor
import info.tduty.typetalk.socket.SocketController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 12.03.2020.
 */
class LoginPresenter(private val view: LoginView,
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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.openMainScreen()
                socketController.connect()
            }, Timber::e)
        disposables.add(disposable)
    }
}
