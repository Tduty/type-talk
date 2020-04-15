package info.tduty.typetalk.view.task.dictionarypicationary.di

import dagger.Subcomponent
import info.tduty.typetalk.view.task.dictionarypicationary.DictionaryPictionaryFragment

@Subcomponent(modules = [DictionaryPictionaryModule::class])
interface DictionaryPictionaryComponent {

    fun inject(view: DictionaryPictionaryFragment)
}