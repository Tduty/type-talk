package info.tduty.typetalk.view.auth.di

import dagger.Subcomponent
import info.tduty.typetalk.view.auth.AuthQRFragment

@Subcomponent(modules = [AuthQRModule::class])
interface AuthQRComponent {

    fun inject(view: AuthQRFragment)
}