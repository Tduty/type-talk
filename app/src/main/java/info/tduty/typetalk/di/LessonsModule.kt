package info.tduty.typetalk.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.view.lesson.LessonPresenter
import info.tduty.typetalk.view.lesson.LessonView

@Module
class LessonsModule(val view: LessonView) {

    @Provides
    fun provideView() : LessonView {
        return view
    }
    @Provides
    fun provideLessonPresenter(view: LessonView) : LessonPresenter {
        return LessonPresenter(view)
    }
}
