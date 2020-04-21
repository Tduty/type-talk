package info.tduty.typetalk.view.task.translation.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.domain.interactor.ChatInteractor
import info.tduty.typetalk.domain.interactor.HistoryInteractor
import info.tduty.typetalk.domain.interactor.LessonInteractor
import info.tduty.typetalk.domain.interactor.TaskInteractor
import info.tduty.typetalk.view.task.translation.TranslationPresenter
import info.tduty.typetalk.view.task.translation.TranslationView

@Module
class TranslationModule(val view: TranslationView) {

    @Provides
    fun provideTranslationPresenter(taskInteractor: TaskInteractor,
                                    chatInteractor: ChatInteractor,
                                    lessonInteractor: LessonInteractor,
                                    historyInteractor: HistoryInteractor): TranslationPresenter {
        return TranslationPresenter(
            view,
            taskInteractor,
            lessonInteractor,
            chatInteractor,
            historyInteractor
        )
    }
}