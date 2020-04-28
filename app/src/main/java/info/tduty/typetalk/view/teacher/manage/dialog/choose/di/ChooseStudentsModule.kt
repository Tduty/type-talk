package info.tduty.typetalk.view.teacher.manage.dialog.choose.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.domain.interactor.StudentInteractor
import info.tduty.typetalk.domain.interactor.teacher.TeacherLessonInteractor
import info.tduty.typetalk.view.teacher.manage.dialog.choose.ChooseStudentsPresenter
import info.tduty.typetalk.view.teacher.manage.dialog.choose.ChooseStudentsView

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
@Module
class ChooseStudentsModule(private val view: ChooseStudentsView) {

    @Provides
    fun provideChooseStudentsPresenter(
        studentInteractor: StudentInteractor,
        lessonInteractor: TeacherLessonInteractor
    ): ChooseStudentsPresenter {
        return ChooseStudentsPresenter(view, studentInteractor, lessonInteractor)
    }
}
