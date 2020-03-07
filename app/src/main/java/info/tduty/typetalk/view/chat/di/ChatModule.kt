package info.tduty.typetalk.view.chat.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.domain.interactor.ChatInteractor
import info.tduty.typetalk.domain.interactor.HistoryInteractor
import info.tduty.typetalk.domain.managers.EventManager
import info.tduty.typetalk.view.chat.ChatPresenter
import info.tduty.typetalk.view.chat.ChatView

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
@Module
class ChatModule(val view: ChatView) {

    @Provides
    fun provideChatPresenter(
        chatInteractor: ChatInteractor,
        historyInteractor: HistoryInteractor,
        eventManager: EventManager
    ): ChatPresenter {
        return ChatPresenter(view, chatInteractor, historyInteractor, eventManager)
    }
}
