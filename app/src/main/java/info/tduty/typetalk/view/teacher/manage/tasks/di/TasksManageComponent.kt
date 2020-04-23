package info.tduty.typetalk.view.teacher.manage.tasks.di

import dagger.Subcomponent
import info.tduty.typetalk.view.teacher.manage.tasks.TasksManageFragment

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
@Subcomponent(modules = [TasksManageModule::class])
interface TasksManageComponent {

    fun inject(fragment: TasksManageFragment)
}