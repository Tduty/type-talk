package info.tduty.typetalk.view.teacher.classinfo.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.domain.interactor.StudentInteractor
import info.tduty.typetalk.domain.managers.EventManager
import info.tduty.typetalk.view.teacher.classinfo.ClassPresenter
import info.tduty.typetalk.view.teacher.classinfo.ClassView

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
@Module
class ClassModule(private val view: ClassView) {

    @Provides
    fun provideClassPresenter(
        studentInteractor: StudentInteractor,
        eventManager: EventManager
    ): ClassPresenter {
        return ClassPresenter(view, studentInteractor, eventManager)
    }
}
