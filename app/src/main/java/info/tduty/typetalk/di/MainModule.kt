package info.tduty.typetalk.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.view.main.MainPresenter
import info.tduty.typetalk.view.main.MainView

@Module
class MainModule(val view: MainView) {

    @Provides
    fun provideView() : MainView {
        return view
    }

    @Provides
    fun provideMainPresenter(view: MainView) : MainPresenter {
        return MainPresenter(view)
    }
}