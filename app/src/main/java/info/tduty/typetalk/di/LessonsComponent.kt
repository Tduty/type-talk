package info.tduty.typetalk.di

import dagger.Component
import info.tduty.typetalk.view.lesson.LessonFragment

@Component(modules = [LessonsModule::class])
interface LessonsComponent {

    fun inject(view: LessonFragment)
}
