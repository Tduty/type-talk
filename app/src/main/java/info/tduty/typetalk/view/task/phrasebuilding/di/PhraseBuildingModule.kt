package info.tduty.typetalk.view.task.phrasebuilding.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.domain.interactor.TaskInteractor
import info.tduty.typetalk.socket.SocketController
import info.tduty.typetalk.view.task.phrasebuilding.PhraseBuildingPresenter
import info.tduty.typetalk.view.task.phrasebuilding.PhraseBuildingView

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
@Module
class PhraseBuildingModule(private val view: PhraseBuildingView) {

    @Provides
    fun providePhraseBuildingPresenter(taskInteractor: TaskInteractor, socketController: SocketController): PhraseBuildingPresenter {
        return PhraseBuildingPresenter(view, taskInteractor, socketController)
    }
}
