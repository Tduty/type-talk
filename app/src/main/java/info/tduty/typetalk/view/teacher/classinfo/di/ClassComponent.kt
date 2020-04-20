package info.tduty.typetalk.view.teacher.classinfo.di

import dagger.Subcomponent
import info.tduty.typetalk.view.teacher.classinfo.ClassFragment

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
@Subcomponent(modules = [ClassModule::class])
interface ClassComponent {

    fun inject(fragment: ClassFragment)
}
