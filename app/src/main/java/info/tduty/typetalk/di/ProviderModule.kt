package info.tduty.typetalk.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.api.*
import info.tduty.typetalk.data.pref.TokenStorage
import info.tduty.typetalk.domain.provider.*
import javax.inject.Singleton

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
@Module
class ProviderModule {

    @Provides
    @Singleton
    fun provideLoginProvider(loginApi: LoginApi): LoginProvider {
        return LoginProvider(loginApi)
    }

    @Provides
    @Singleton
    fun provideLessonProvider(lessonApi: LessonApi, tokenStorage: TokenStorage): LessonProvider {
        return LessonProvider(lessonApi, tokenStorage)
    }

    @Provides
    @Singleton
    fun provideHistoryProvider(
        historyApi: HistoryApi,
        tokenStorage: TokenStorage
    ): HistoryProvider {
        return HistoryProvider(historyApi, tokenStorage)
    }

    @Provides
    @Singleton
    fun provideClassProvider(classApi: ClassApi, tokenStorage: TokenStorage): ClassProvider {
        return ClassProvider(classApi, tokenStorage)
    }

    @Provides
    @Singleton
    fun provideChatProvider(chatApi: ChatApi, tokenStorage: TokenStorage): ChatProvider {
        return ChatProvider(chatApi, tokenStorage)
    }

    @Provides
    @Singleton
    fun provideDictionaryProvider(
        dictionaryApi: DictionaryApi,
        tokenStorage: TokenStorage
    ): DictionaryProvider {
        return DictionaryProvider(dictionaryApi, tokenStorage)
    }
}
