package info.tduty.typetalk.view.teacher.manage.dialog.list.di

import dagger.Subcomponent
import info.tduty.typetalk.view.teacher.manage.dialog.list.DialogsFragment

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
@Subcomponent(modules = [DialogsModule::class])
interface DialogsComponent {

    fun inject(fragment: DialogsFragment)
}