package info.tduty.typetalk.view.chat

import android.content.Context
import info.tduty.typetalk.R
import info.tduty.typetalk.data.db.model.ChatEntity
import info.tduty.typetalk.data.model.ChatVO
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
    private val context: Context,
    private val chatInteractor: ChatInteractor,
    private val historyInteractor: HistoryInteractor,
    private val eventManager: EventManager
) {

    private var chatId: String? = null
    private val disposables = CompositeDisposable()

    fun onCreate(chatId: String?, type: String) {
        this.chatId = chatId

        when (type) {
            ChatEntity.CLASS_CHAT -> loadClassChat()
            ChatEntity.TEACHER_CHAT -> loadTeacherChat()
            else -> if (chatId != null) loadChat(chatId)
        }

        if (chatId != null) {
            getHistory(chatId, type)
            listenMessageNew(chatId)
        }
    }

    fun onDestroy() {
        disposables.dispose()
    }

    fun sendMessage(message: String) {
        if (message.isBlank()) return
        chatId?.let { historyInteractor.sendMessage(it, message) }
    }

    private fun loadChat(chatId: String) {
        disposables.add(
            chatInteractor.getChat(chatId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ chat -> setupChat(chat) }, Timber::e)
        )
    }

    private fun loadTeacherChat() {
        disposables.add(
            chatInteractor.getTeacherChat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ chat -> setupChat(chat) }, Timber::e)
        )
    }

    private fun loadClassChat() {
        disposables.add(
            chatInteractor.getClassChat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ chat -> setupChat(chat) }, Timber::e)
        )
    }

    private fun setupChat(chat: ChatVO) {
        if (chatId == null) {
            chatId = chat.chatId
            getHistory(chat.chatId, chat.type)
            listenMessageNew(chat.chatId)
        }
        setupToolbar(chat)
    }

    private fun setupToolbar(chat: ChatVO) {
        if (chat.isTeacherChat) {
            view.setToolbarTitle(context.getString(R.string.main_chat_teacher_title))
        } else {
            view.showTeacherMenu()
            view.setToolbarTitle(chat.title)
        }
        if (chat.avatarURL != null) view.setToolbarIcon(chat.avatarURL)
    }

    private fun getHistory(chatId: String, chatType: String) {
        disposables.add(
            historyInteractor.getHistory(chatId, chatType)
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