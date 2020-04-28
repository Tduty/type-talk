package info.tduty.typetalk.view.teacher.manage.lessons.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.domain.interactor.teacher.TeacherLessonInteractor
import info.tduty.typetalk.view.teacher.manage.lessons.LessonsManagePresenter
import info.tduty.typetalk.view.teacher.manage.lessons.LessonsManageView

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
@Module
class LessonsManageModule(private val view: LessonsManageView) {

    @Provides
    fun provideLessonsManagePresenter(teacherLessonInteractor: TeacherLessonInteractor): LessonsManagePresenter {
        return LessonsManagePresenter(view, teacherLessonInteractor)
    }
}
