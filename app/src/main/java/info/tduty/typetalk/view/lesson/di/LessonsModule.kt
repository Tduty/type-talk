package info.tduty.typetalk.view.lesson.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.domain.interactor.LessonInteractor
import info.tduty.typetalk.domain.interactor.TaskInteractor
import info.tduty.typetalk.domain.managers.EventManager
import info.tduty.typetalk.socket.SocketController
import info.tduty.typetalk.view.lesson.LessonPresenter
import info.tduty.typetalk.view.lesson.LessonView

@Module
class LessonsModule(val view: LessonView) {

    @Provides
    fun provideView() : LessonView {
        return view
    }
    @Provides
    fun provideLessonPresenter(view: LessonView, taskInteractor: TaskInteractor,
                               lessonInteractor: LessonInteractor,
                               eventManager: EventManager) : LessonPresenter {
        return LessonPresenter(view, taskInteractor, lessonInteractor, eventManager)
    }
}
