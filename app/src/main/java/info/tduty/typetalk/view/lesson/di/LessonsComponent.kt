package info.tduty.typetalk.view.lesson.di

import dagger.Subcomponent
import info.tduty.typetalk.view.lesson.LessonFragment

@Subcomponent(modules = [LessonsModule::class])
interface LessonsComponent {

    fun inject(view: LessonFragment)
}
