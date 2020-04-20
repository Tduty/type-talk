package info.tduty.typetalk.view.teacher.main.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.domain.interactor.ClassInteractor
import info.tduty.typetalk.view.teacher.main.MainTeacherPresenter
import info.tduty.typetalk.view.teacher.main.MainTeacherView

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
@Module
class MainTeacherModule(val view: MainTeacherView) {

    @Provides
    fun provideMainTeacherPresenter(classInteractor: ClassInteractor): MainTeacherPresenter {
        return MainTeacherPresenter(
            view,
            classInteractor
        )
    }
}