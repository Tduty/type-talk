package info.tduty.typetalk.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.api.LessonApi
import info.tduty.typetalk.data.db.wrapper.LessonWrapper
import info.tduty.typetalk.domain.interactor.LessonInteractor
import info.tduty.typetalk.domain.managers.LessonProvider
import javax.inject.Singleton

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
@Module
class InteractorModule {

    @Provides
    @Singleton
    fun provideLessonProvider(lessonApi: LessonApi): LessonProvider {
        return LessonProvider(lessonApi)
    }

    @Provides
    @Singleton
    fun provideLessonInteractor(
        lessonProvider: LessonProvider,
        lessonWrapper: LessonWrapper
    ): LessonInteractor {
        return LessonInteractor(lessonProvider, lessonWrapper)
    }
}