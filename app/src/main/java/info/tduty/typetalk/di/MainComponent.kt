package info.tduty.typetalk.di

import dagger.Component
import info.tduty.typetalk.view.main.MainFragment

@Component(modules = [MainModule::class])
interface MainComponent {

    fun inject(main: MainFragment)
}
