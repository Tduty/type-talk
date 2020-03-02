package info.tduty.typetalk.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.view.dictionary.DictionaryPresenter
import info.tduty.typetalk.view.dictionary.DictionaryView


@Module
class DictionaryModule(val view: DictionaryView) {

    @Provides
    fun provideView() : DictionaryView {
        return view
    }

    @Provides
    fun provideDictionaryPresenter(view: DictionaryView) : DictionaryPresenter {
        return DictionaryPresenter(view)
    }
}