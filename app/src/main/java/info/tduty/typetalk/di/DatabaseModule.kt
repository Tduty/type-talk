package info.tduty.typetalk.di

import android.content.Context
import dagger.Module
import dagger.Provides
import info.tduty.typetalk.data.db.AppDatabase
import info.tduty.typetalk.data.db.DataBaseBuilderRoom
import info.tduty.typetalk.data.db.dao.*
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
    fun provideClassDao(database: AppDatabase): ClassDao {
        return database.getClassDao()
    }

    @Provides
    @Singleton
    fun provideStudentDao(database: AppDatabase): StudentDao {
        return database.getStudentDao()
    }

    @Provides
    @Singleton
    fun provideLessonDao(database: AppDatabase): LessonDao {
        return database.getLessonsDao()
    }

    @Provides
    @Singleton
    fun provideTaskDao(database: AppDatabase): TaskDao {
        return database.getTaskDao()
    }

    @Provides
    @Singleton
    fun provideMessageDao(database: AppDatabase): MessageDao {
        return database.getMessageDao()
    }

    @Provides
    @Singleton
    fun provideChatDao(database: AppDatabase): ChatDao {
        return database.getChatDao()
    }

    @Provides
    @Singleton
    fun provideDictionaryDao(database: AppDatabase): DictionaryDao {
        return database.getDictionaryDao()
    }
}
