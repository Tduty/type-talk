package info.tduty.typetalk.view.task.dictionarypicationary.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.domain.interactor.TaskInteractor
import info.tduty.typetalk.view.task.dictionarypicationary.DictionaryPictionaryPresenter
import info.tduty.typetalk.view.task.dictionarypicationary.DictionaryPictionaryView

@Module
class DictionaryPictionaryModule(val view: DictionaryPictionaryView) {

    @Provides
    fun provideDictionaryPictionaryPresenter(taskInteractor: TaskInteractor): DictionaryPictionaryPresenter {
        return DictionaryPictionaryPresenter(
            view,
            taskInteractor
        )
    }
}