package info.tduty.typetalk.view.login.password.qr.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.domain.interactor.LoginInteractor
import info.tduty.typetalk.domain.managers.DataLoaderManager
import info.tduty.typetalk.socket.SocketController
import info.tduty.typetalk.view.login.password.qr.AuthQRFragment
import info.tduty.typetalk.view.login.password.qr.AuthQRPresenter

@Module
class AuthQRModule(val view: AuthQRFragment) {

    @Provides
    fun provideAuthQRPresenter(
        dataLoaderManager: DataLoaderManager,
        socketController: SocketController,
        loginInteractor: LoginInteractor
    ): AuthQRPresenter {
        return AuthQRPresenter(view, dataLoaderManager, socketController, loginInteractor)
    }
}
