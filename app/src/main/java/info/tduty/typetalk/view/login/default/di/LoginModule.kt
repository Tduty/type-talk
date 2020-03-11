package info.tduty.typetalk.view.login.default.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.view.login.default.LoginPresenter
import info.tduty.typetalk.view.login.default.LoginView

/**
 * Created by Evgeniy Mezentsev on 12.03.2020.
 */
@Module
class LoginModule(val view: LoginView) {

    @Provides
    fun provideLoginPresenter(): LoginPresenter {
        return LoginPresenter(view)
    }
}
