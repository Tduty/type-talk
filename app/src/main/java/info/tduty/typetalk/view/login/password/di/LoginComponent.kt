package info.tduty.typetalk.view.login.password.di

import dagger.Subcomponent
import info.tduty.typetalk.view.login.password.LoginFragment

/**
 * Created by Evgeniy Mezentsev on 12.03.2020.
 */
@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {

    fun inject(view: LoginFragment)
}
