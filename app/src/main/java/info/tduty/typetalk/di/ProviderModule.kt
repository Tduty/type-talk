package info.tduty.typetalk.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.api.ChatApi
import info.tduty.typetalk.api.HistoryApi
import info.tduty.typetalk.api.LessonApi
import info.tduty.typetalk.domain.provider.ChatProvider
import info.tduty.typetalk.domain.provider.HistoryProvider
import info.tduty.typetalk.domain.provider.LessonProvider
import javax.inject.Singleton

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
@Module
class ProviderModule {

    @Provides
    @Singleton
    fun provideLessonProvider(lessonApi: LessonApi): LessonProvider {
        return LessonProvider(lessonApi)
    }

    @Provides
    @Singleton
    fun provideHistoryProvider(historyApi: HistoryApi): HistoryProvider {
        return HistoryProvider(historyApi)
    }

    @Provides
    @Singleton
    fun provideChatProvider(chatApi: ChatApi): ChatProvider {
        return ChatProvider(chatApi)
    }
}
