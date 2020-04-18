package info.tduty.typetalk.view.teacher.main.di

import dagger.Subcomponent
import info.tduty.typetalk.view.teacher.main.MainTeacherFragment

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
@Subcomponent(modules = [MainTeacherModule::class])
interface MainTeacherComponent {

    fun inject(main: MainTeacherFragment)
}