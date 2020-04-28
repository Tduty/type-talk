package info.tduty.typetalk.di

import android.content.Context
import dagger.Module
import dagger.Provides
import info.tduty.typetalk.data.pref.UserDataHelper
import info.tduty.typetalk.domain.mapper.ChatMapper
import info.tduty.typetalk.domain.mapper.TaskPayloadMapperStrategy
import javax.inject.Singleton

@Module
class MapperModule {

    @Provides
    @Singleton
    fun provideTaskMapperStrategy(): TaskPayloadMapperStrategy = TaskPayloadMapperStrategy()

    @Provides
    @Singleton
    fun provideChatMapper(context: Context, userDataHelper: UserDataHelper): ChatMapper {
        return ChatMapper(context, userDataHelper)
    }
}