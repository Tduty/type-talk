package info.tduty.typetalk.view.task.wordamess.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.domain.interactor.TaskInteractor
import info.tduty.typetalk.socket.SocketController
import info.tduty.typetalk.view.task.wordamess.WordamessPresenter
import info.tduty.typetalk.view.task.wordamess.WordamessView

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
@Module
class WordamessModule(val view: WordamessView) {

    @Provides
    fun provideWordamessPresenter(taskInteractor: TaskInteractor, socketController: SocketController): WordamessPresenter {
        return WordamessPresenter(view, taskInteractor, socketController)
    }
}
