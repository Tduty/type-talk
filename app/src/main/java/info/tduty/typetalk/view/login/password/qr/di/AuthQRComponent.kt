package info.tduty.typetalk.view.login.password.qr.di

import dagger.Subcomponent
import info.tduty.typetalk.view.login.password.qr.AuthQRFragment

@Subcomponent(modules = [AuthQRModule::class])
interface AuthQRComponent {

    fun inject(view: AuthQRFragment)
}