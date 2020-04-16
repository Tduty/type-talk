package info.tduty.typetalk.view.task.hurryup.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.domain.interactor.TaskInteractor
import info.tduty.typetalk.view.task.hurryup.HurryUpPresenter
import info.tduty.typetalk.view.task.hurryup.HurryUpView

@Module
class HurryUpModule(private val view: HurryUpView) {

    @Provides
    fun providePhraseBuildingPresenter(taskInteractor: TaskInteractor): HurryUpPresenter {
        return HurryUpPresenter(view, taskInteractor)
    }
}