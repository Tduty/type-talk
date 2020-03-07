package info.tduty.typetalk.view.dictionary.di

import dagger.Subcomponent
import info.tduty.typetalk.view.dictionary.DictionaryFragment

@Subcomponent(modules = [DictionaryModule::class])
interface DictionaryComponent {

    fun inject(view: DictionaryFragment)
}