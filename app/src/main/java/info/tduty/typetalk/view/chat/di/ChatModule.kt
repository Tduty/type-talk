package info.tduty.typetalk.view.chat.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.data.pref.UserDataHelper
import info.tduty.typetalk.domain.interactor.ChatInteractor
import info.tduty.typetalk.domain.interactor.HistoryInteractor
import info.tduty.typetalk.domain.managers.EventManager
import info.tduty.typetalk.view.chat.ChatFragment
import info.tduty.typetalk.view.chat.ChatPresenter

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
@Module
class ChatModule(val chatFragment: ChatFragment) {

    @Provides
    fun provideChatPresenter(
        chatInteractor: ChatInteractor,
        historyInteractor: HistoryInteractor,
        eventManager: EventManager,
        userDataHelper: UserDataHelper
    ): ChatPresenter {
        return ChatPresenter(chatFragment, chatFragment.requireContext(), chatInteractor,
            historyInteractor, eventManager, userDataHelper)
    }
}
