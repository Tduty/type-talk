package info.tduty.typetalk.view.task.hurryup.di

import dagger.Subcomponent
import info.tduty.typetalk.view.task.hurryup.HurryUpFragment

@Subcomponent(modules = [HurryUpModule::class])
interface HurryUpComponent {

    fun inject(view: HurryUpFragment)
}