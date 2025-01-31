package info.tduty.typetalk.view.dictionary.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.domain.interactor.DictionaryInteractor
import info.tduty.typetalk.view.dictionary.DictionaryPresenter
import info.tduty.typetalk.view.dictionary.DictionaryView

@Module
class DictionaryModule(val view: DictionaryView) {

    @Provides
    fun provideView() : DictionaryView {
        return view
    }

    @Provides
    fun provideDictionaryPresenter(view: DictionaryView,
                                   dictionaryInteractor: DictionaryInteractor) : DictionaryPresenter {
        return DictionaryPresenter(view, dictionaryInteractor)
    }
}