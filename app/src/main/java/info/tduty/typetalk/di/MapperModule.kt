package info.tduty.typetalk.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.mapper.TaskPayloadMapperStrategy
import javax.inject.Singleton

@Module
class MapperModule {

    @Provides
    @Singleton
    fun provideTaskMapperStrategy(): TaskPayloadMapperStrategy = TaskPayloadMapperStrategy()
}