package info.tduty.typetalk.view.main.di

import dagger.Subcomponent
import info.tduty.typetalk.view.main.MainFragment

@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    fun inject(main: MainFragment)
}
