package info.tduty.typetalk.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.data.db.dao.ChatDao
import info.tduty.typetalk.data.db.dao.LessonDao
import info.tduty.typetalk.data.db.dao.MessageDao
import info.tduty.typetalk.data.db.dao.TaskDao
import info.tduty.typetalk.data.db.wrapper.ChatWrapper
import info.tduty.typetalk.data.db.wrapper.LessonWrapper
import info.tduty.typetalk.data.db.wrapper.MessageWrapper
import info.tduty.typetalk.data.db.wrapper.TaskWrapper
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
}