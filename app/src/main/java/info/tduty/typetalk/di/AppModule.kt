package info.tduty.typetalk.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.view.dictionary.DictionaryPresenter
import info.tduty.typetalk.view.dictionary.DictionaryView
import info.tduty.typetalk.view.lesson.LessonPresenter
import info.tduty.typetalk.view.lesson.LessonView
import info.tduty.typetalk.view.main.MainPresenter
import info.tduty.typetalk.view.main.MainView

@Module
class AppModule {

    @Provides
    fun getDictionaryPresenter() : DictionaryPresenter {
        return DictionaryPresenter()
    }

    @Provides
    fun getLessonPresenter() : LessonPresenter {
        return LessonPresenter()
    }

    @Provides
    fun getMainPresenter() : MainPresenter {
        return MainPresenter()
    }
}
