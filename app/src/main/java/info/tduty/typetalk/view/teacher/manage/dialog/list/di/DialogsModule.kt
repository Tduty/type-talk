package info.tduty.typetalk.view.teacher.manage.dialog.list.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.view.teacher.manage.dialog.list.DialogsPresenter
import info.tduty.typetalk.view.teacher.manage.dialog.list.DialogsView

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
@Module
class DialogsModule(private val view: DialogsView) {

    @Provides
    fun provideDialogsPresenter(): DialogsPresenter {
        return DialogsPresenter(view)
    }
}