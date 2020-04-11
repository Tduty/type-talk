package info.tduty.typetalk.view.login.password.qr.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.domain.interactor.LoginInteractor
import info.tduty.typetalk.socket.SocketController
import info.tduty.typetalk.view.login.password.LoginPresenter
import info.tduty.typetalk.view.login.password.qr.AuthQRFragment
import info.tduty.typetalk.view.login.password.qr.AuthQRPresenter
import info.tduty.typetalk.view.login.password.qr.AuthQRView

@Module
class AuthQRModule(val view: AuthQRFragment) {

    @Provides
    fun provideAuthQRPresenter(
        socketController: SocketController,
        loginInteractor: LoginInteractor
    ): AuthQRPresenter {
        return AuthQRPresenter(view, socketController, loginInteractor)
    }
}
