package info.tduty.typetalk.view.task.translation.di

import dagger.Subcomponent
import info.tduty.typetalk.view.task.translation.TranslationFragment

@Subcomponent(modules = [TranslationModule::class])
interface TranslationComponent {

    fun inject(view: TranslationFragment)
}