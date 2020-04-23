package info.tduty.typetalk.view.teacher.manage.dialog.choose.di

import dagger.Subcomponent
import info.tduty.typetalk.view.teacher.manage.dialog.choose.ChooseStudentsFragment

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
@Subcomponent(modules = [ChooseStudentsModule::class])
interface ChooseStudentsComponent {

    fun inject(fragment: ChooseStudentsFragment)
}
