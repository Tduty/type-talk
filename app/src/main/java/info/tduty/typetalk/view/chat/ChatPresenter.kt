package info.tduty.typetalk.view.chat

import info.tduty.typetalk.domain.interactor.ChatInteractor
import info.tduty.typetalk.domain.interactor.HistoryInteractor
import info.tduty.typetalk.domain.managers.EventManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */
class ChatPresenter(
    private val view: ChatView,
    private val chatInteractor: ChatInteractor,
    private val historyInteractor: HistoryInteractor,
    private val eventManager: EventManager
) {

    private lateinit var chatId: String
    private val disposables = CompositeDisposable()

    fun onCreate(chatId: String) {
        this.chatId = chatId

        setupChat(chatId)
        getHistory(chatId)
        listenMessageNew(chatId)

    }

    fun onDestroy() {
        disposables.dispose()
    }

    fun sendMessage(message: String) {
        if (message.isBlank()) return
        historyInteractor.sendMessage(chatId, message)
    }

    private fun setupChat(chatId: String) {
        disposables.add(
            chatInteractor.getChat(chatId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ chat ->
                    view.setToolbarTitle(chat.title)
                    chat.avatarURL?.let { view.setToolbarIcon(it) }
                }, Timber::e)
        )
    }

    private fun getHistory(chatId: String) {
        disposables.add(
            historyInteractor.getHistory(chatId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view.setEvents(it) }, Timber::e)
        )
    }

    private fun listenMessageNew(chatId: String) {
        disposables.add(
            eventManager.messageNew()
                .filter { chatId == it.chatId }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view.addEvent(it) }, Timber::e)
        )
    }
}