package info.tduty.typetalk.di

import dagger.Component
import info.tduty.typetalk.view.dictionary.DictionaryFragment
import info.tduty.typetalk.view.lesson.LessonFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun injectPresenterForDictionaryFragment(main: DictionaryFragment?)
    fun injectPresenterForLessonFragment(main: LessonFragment?)
}
