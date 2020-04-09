package info.tduty.typetalk.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.data.db.wrapper.ChatWrapper
import info.tduty.typetalk.data.db.wrapper.LessonWrapper
import info.tduty.typetalk.data.db.wrapper.MessageWrapper
import info.tduty.typetalk.data.db.wrapper.TaskWrapper
import info.tduty.typetalk.data.pref.TokenStorage
import info.tduty.typetalk.data.pref.UserDataHelper
import info.tduty.typetalk.domain.interactor.*
import info.tduty.typetalk.domain.managers.EventManager
import info.tduty.typetalk.domain.provider.ChatProvider
import info.tduty.typetalk.domain.provider.HistoryProvider
import info.tduty.typetalk.domain.provider.LessonProvider
import info.tduty.typetalk.domain.provider.LoginProvider
import info.tduty.typetalk.socket.SocketController
import javax.inject.Singleton

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
@Module
class InteractorModule {

    @Provides
    @Singleton
    fun provideLessonInteractor(
        lessonProvider: LessonProvider,
        lessonWrapper: LessonWrapper,
        taskWrapper: TaskWrapper,
        eventManager: EventManager
    ): LessonInteractor {
        return LessonInteractor(lessonProvider, lessonWrapper, taskWrapper, eventManager)
    }

    @Provides
    @Singleton
    fun provideHistoryInteractior(
        historyProvider: HistoryProvider,
        messageWrapper: MessageWrapper,
        chatWrapper: ChatWrapper,
        userDataHelper: UserDataHelper,
        eventManager: EventManager,
        socketController: SocketController
    ): HistoryInteractor {
        return HistoryInteractor(
            historyProvider, messageWrapper, chatWrapper, userDataHelper, eventManager,
            socketController
        )
    }

    @Provides
    @Singleton
    fun provideChatInteractor(
        chatWrapper: ChatWrapper,
        chatProvider: ChatProvider
    ): ChatInteractor {
        return ChatInteractor(chatWrapper, chatProvider)
    }

    @Provides
    @Singleton
    fun provideTaskInteractor(
        taskWrapper: TaskWrapper
    ): TaskInteractor {
        return TaskInteractor(taskWrapper)
    }

    @Provides
    @Singleton
    fun provideLoginInteractor(
        userDataHelper: UserDataHelper,
        tokenStorage: TokenStorage,
        loginProvider: LoginProvider
    ): LoginInteractor {
        return LoginInteractor(userDataHelper, tokenStorage, loginProvider)
    }
}