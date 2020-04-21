package info.tduty.typetalk.view.chat

import android.content.Context
import info.tduty.typetalk.R
import info.tduty.typetalk.data.db.model.ChatEntity
import info.tduty.typetalk.data.model.ChatVO
import info.tduty.typetalk.data.model.CleanBadge
import info.tduty.typetalk.data.model.CorrectionVO
import info.tduty.typetalk.data.model.MessageVO
import info.tduty.typetalk.data.pref.UserDataHelper
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
    private val eventManager: EventManager,
    private val userDataHelper: UserDataHelper
) {

    private var chatId: String? = null
    private val disposables = CompositeDisposable()

    private var correctionId: String? = null
    private var correctionType: CorrectionVO.AdditionalType = CorrectionVO.AdditionalType.NONE

    fun onCreate(chatId: String?, type: String) {
        this.chatId = chatId

        when (type) {
            ChatEntity.CLASS_CHAT -> loadClassChat()
            ChatEntity.TEACHER_CHAT -> loadTeacherChat()
            else -> if (chatId != null) loadChat(chatId)
        }

        if (chatId != null) {
            cleanBadge()
            getHistory(chatId, type)
            listenMessageNew(chatId)
        }
    }

    fun onDestroy() {
        cleanBadge()
        disposables.dispose()
    }

    fun onMessageClick(messageVO: MessageVO) {
        if (userDataHelper.isTeacher()) {
            view.showMessageActionDialog(messageVO)
        }
    }

    fun onCorrectMessage(messageVO: MessageVO) {
        setupCorrection(messageVO, CorrectionVO.AdditionalType.CORRECTION)
    }

    fun onCommentMessage(messageVO: MessageVO) {
        setupCorrection(messageVO, CorrectionVO.AdditionalType.COMMENT)
    }

    fun onSendBtnClick(message: String) {
        if (correctionId == null) sendMessage(message)
        else sendCorrectionMessage(message)
    }

    fun cancelCorrection() {
        removeCorrection()
    }

    private fun sendMessage(message: String) {
        if (message.isBlank()) return
        chatId?.let { historyInteractor.sendMessage(it, message) }
    }

    private fun sendCorrectionMessage(message: String) {
        if (message.isBlank()) return
        val syncId = correctionId ?: return
        val chatId = chatId ?: return
        historyInteractor.sendCorrection(syncId, chatId, message, correctionType)
        removeCorrection()
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
        disposables.add(
            eventManager.correctMessage()
                .filter { chatId == it.chatId }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view.updateCorrection(it) }, Timber::e)
        )
    }

    private fun cleanBadge() {
        chatId?.let { eventManager.post(CleanBadge(it)) }
    }

    private fun setupCorrection(messageVO: MessageVO, type: CorrectionVO.AdditionalType) {
        correctionId = messageVO.id
        correctionType = type
        view.showCorrectionState(messageVO.senderName, messageVO.message, correctionType)
    }

    private fun removeCorrection() {
        correctionId = null
        correctionType = CorrectionVO.AdditionalType.NONE
        view.hideCorrectionState()
    }
}