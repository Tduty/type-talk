package info.tduty.typetalk.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.data.db.dao.*
import info.tduty.typetalk.data.db.wrapper.*
import javax.inject.Singleton

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
@Module
class WrapperModule {

    @Provides
    @Singleton
    fun provideClassWrapper(classDao: ClassDao): ClassWrapper {
        return ClassWrapper(classDao)
    }

    @Provides
    @Singleton
    fun provideStudentWrapper(studentDao: StudentDao): StudentWrapper {
        return StudentWrapper(studentDao)
    }

    @Provides
    @Singleton
    fun provideLessonWrapper(lessonDao: LessonDao): LessonWrapper {
        return LessonWrapper(lessonDao)
    }

    @Provides
    @Singleton
    fun provideMessageWrapper(messageDao: MessageDao): MessageWrapper {
        return MessageWrapper(messageDao)
    }

    @Provides
    @Singleton
    fun provideChatWrapper(chatDao: ChatDao): ChatWrapper {
        return ChatWrapper(chatDao)
    }

    @Provides
    @Singleton
    fun provideTaskWrapper(taskDao: TaskDao): TaskWrapper {
        return TaskWrapper(taskDao)
    }

    @Provides
    @Singleton
    fun provideDictionaryWrapper(dictionaryDao: DictionaryDao): DictionaryWrapper {
        return DictionaryWrapper(dictionaryDao)
    }
}
