package info.tduty.typetalk.view.task.flashcard.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.domain.interactor.LoginInteractor
import info.tduty.typetalk.domain.interactor.TaskInteractor
import info.tduty.typetalk.socket.SocketController
import info.tduty.typetalk.view.login.password.LoginPresenter
import info.tduty.typetalk.view.task.flashcard.FlashcardPresenter
import info.tduty.typetalk.view.task.flashcard.FlashcardView

@Module
class FlashcardModule(val view: FlashcardView) {

    @Provides
    fun provideFlashcardPresenter(taskInteractor: TaskInteractor): FlashcardPresenter {
        return FlashcardPresenter(view, taskInteractor)
    }
}