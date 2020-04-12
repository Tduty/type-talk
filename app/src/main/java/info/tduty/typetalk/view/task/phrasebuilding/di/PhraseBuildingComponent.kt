package info.tduty.typetalk.view.task.phrasebuilding.di

import dagger.Subcomponent
import info.tduty.typetalk.view.task.phrasebuilding.PhraseBuildingFragment

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
@Subcomponent(modules = [PhraseBuildingModule::class])
interface PhraseBuildingComponent {

    fun inject(view: PhraseBuildingFragment)
}
