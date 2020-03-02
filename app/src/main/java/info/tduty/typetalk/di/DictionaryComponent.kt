package info.tduty.typetalk.di

import dagger.Component
import info.tduty.typetalk.view.dictionary.DictionaryFragment

@Component(modules = [DictionaryModule::class])
interface DictionaryComponent {

    fun inject(view: DictionaryFragment)
}