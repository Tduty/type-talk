package info.tduty.typetalk.view.teacher.manage.tasks.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.domain.interactor.teacher.TeacherDialogInteractor
import info.tduty.typetalk.domain.interactor.teacher.TeacherLessonInteractor
import info.tduty.typetalk.view.teacher.manage.tasks.TasksManagePresenter
import info.tduty.typetalk.view.teacher.manage.tasks.TasksManageView

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
@Module
class TasksManageModule(private val view: TasksManageView) {

    @Provides
    fun provideTasksManagePresenter(
        teacherLessonInteractor: TeacherLessonInteractor,
        dialogInteractor: TeacherDialogInteractor
    ): TasksManagePresenter {
        return TasksManagePresenter(view, teacherLessonInteractor, dialogInteractor)
    }
}