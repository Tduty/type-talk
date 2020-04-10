package info.tduty.typetalk.view.login.password.qr

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import info.tduty.typetalk.domain.interactor.LoginInteractor
import info.tduty.typetalk.socket.SocketController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class AuthQRPresenter(private val view: AuthQRView,
                      private val socketController: SocketController,
                      private val loginInteractor: LoginInteractor) {
    private val disposables = CompositeDisposable()

    fun onCreate() {
        view.showScanCamera()
    }

    fun onDestroy() {
        disposables.dispose()
    }

    fun onNext(data: String) {
        view.showLoading()
        val disposable = loginInteractor.auth(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.openMainScreen()
                socketController.connect()
            }, Timber::e)
        disposables.add(disposable)
    }

    fun onResume() {
        if (!view.isLoading()) view.showScanCamera()
    }
}
