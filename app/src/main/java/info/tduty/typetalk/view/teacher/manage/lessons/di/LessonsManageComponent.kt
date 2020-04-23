package info.tduty.typetalk.view.teacher.manage.lessons.di

import dagger.Subcomponent
import info.tduty.typetalk.view.teacher.manage.lessons.LessonsManageFragment

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
@Subcomponent(modules = [LessonsManageModule::class])
interface LessonsManageComponent {

    fun inject(fragment: LessonsManageFragment)
}
