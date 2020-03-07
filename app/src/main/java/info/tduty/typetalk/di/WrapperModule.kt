package info.tduty.typetalk.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.data.db.dao.LessonDao
import info.tduty.typetalk.data.db.wrapper.LessonWrapper
import javax.inject.Singleton

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
@Module
class WrapperModule {

    @Provides
    @Singleton
    fun provideLessonWrapper(lessonDao: LessonDao): LessonWrapper {
        return LessonWrapper(lessonDao)
    }
}