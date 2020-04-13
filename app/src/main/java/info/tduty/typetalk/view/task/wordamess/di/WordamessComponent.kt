package info.tduty.typetalk.view.task.wordamess.di

import dagger.Subcomponent
import info.tduty.typetalk.view.task.wordamess.WordamessFragment

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
@Subcomponent(modules = [WordamessModule::class])
interface WordamessComponent {

    fun inject(view: WordamessFragment)
}
