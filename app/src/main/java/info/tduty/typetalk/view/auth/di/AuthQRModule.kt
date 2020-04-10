package info.tduty.typetalk.view.auth.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.view.auth.AuthQRFragment
import info.tduty.typetalk.view.auth.AuthQRPresenter
import info.tduty.typetalk.view.auth.AuthQRView

@Module
class AuthQRModule(val view: AuthQRFragment) {

    @Provides
    fun provideView() : AuthQRFragment {
        return view
    }

    @Provides
    fun provideDictionaryPresenter(view: AuthQRView) : AuthQRPresenter {
        return AuthQRPresenter(view)
    }
}