package info.tduty.typetalk.di

import android.content.Context
import dagger.Module
import dagger.Provides
import info.tduty.typetalk.data.db.AppDatabase
import info.tduty.typetalk.data.db.DataBaseBuilderRoom
import info.tduty.typetalk.data.db.dao.LessonDao
import javax.inject.Singleton

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return DataBaseBuilderRoom(context).build()
    }

    @Provides
    @Singleton
    fun provideLessonDao(database: AppDatabase): LessonDao {
        return database.getLessonsDao()
    }
}
