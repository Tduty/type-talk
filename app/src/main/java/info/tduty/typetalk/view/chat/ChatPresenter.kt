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
import info.tduty.typetalk.extenstion.hasRussianSymbols
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
    private var chatType: String? = null
    private val disposables = CompositeDisposable()

    private var correctionId: String? = null
    private var correctionType: CorrectionVO.AdditionalType = CorrectionVO.AdditionalType.NONE
    private var limitMessageCount: Int? = null
    private var limitMessages = hashSetOf<String>()

    fun onCreate(chatStarter: ChatStarter) {
        chatStarter.chatId?.let { setupChat(it, chatStarter.chatType) }
        handleStarter(chatStarter)
        loadChat(chatId, chatStarter.chatType)
    }

    fun onDestroy() {
        cleanBadge()
        disposables.dispose()
    }

    fun onMessageClick(messageVO: MessageVO) {
        if (userDataHelper.isTeacher()) view.showMessageActionDialog(messageVO)
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

    fun onChangeEditText(text: String) {
        if (userDataHelper.isTeacher() || chatType == ChatEntity.TEACHER_CHAT) return
        if (text.hasRussianSymbols()) {
            view.clearUserInput()
            view.showErrorAboutRussianSymbols()
        }
    }

    fun cancelCorrection() {
        removeCorrection()
    }

    private fun handleStarter(starter: ChatStarter) {
        starter.getFirstEvent()?.let { view.addEventToStart(it) }
        if (starter.isNotActiveInput()) view.hideUserInput()
        limitMessageCount = starter.countMessages
    }

    private fun sendMessage(message: String) {
        if (message.isBlank()) return
        chatId?.let {
            val id = historyInteractor.sendMessage(it, message)
            if (id != null) handleLimitMessages(id)
        }
    }

    private fun sendCorrectionMessage(message: String) {
        if (message.isBlank()) return
        val syncId = correctionId ?: return
        val chatId = chatId ?: return
        historyInteractor.sendCorrection(syncId, chatId, message, correctionType)
        removeCorrection()
    }

    private fun loadChat(chatId: String?, chatType: String) {
        disposables.add(
            chatInteractor.getChat(chatId, chatType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ chat ->
                    setupChat(chat.chatId, chatType)
                    setupToolbar(chat)
                }, Timber::e)
        )
    }

    private fun setupChat(chatId: String, chatType: String) {
        if (this.chatId != null) return
        this.chatId = chatId
        this.chatType = chatType
        getHistory(chatId, chatType)
        listenMessageNew(chatId)
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
                .subscribe({
                    view.addEvents(it)
                    handleLimitMessages(it)
                }, Timber::e)
        )
    }

    private fun listenMessageNew(chatId: String) {
        disposables.add(
            eventManager.messageNew()
                .filter { chatId == it.chatId }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.addEvent(it)
                    handleLimitMessages(it.id, it.isMy)
                }, Timber::e)
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

    private fun handleLimitMessages(messages: List<MessageVO>) {
        if (messages.isEmpty() || messages.all { !it.isMy }) {
            limitMessageCount?.let { view.showCountMessages(it, messages.isNotEmpty()) }
        }
        messages.forEach { handleLimitMessages(it.id, it.isMy) }
    }

    private fun handleLimitMessages(id: String, isMy: Boolean = true) {
        val limitMessageCount = this.limitMessageCount
        if (!isMy || limitMessageCount == null) return
        limitMessages.add(id)
        view.showCountMessages(limitMessageCount - limitMessages.size, true)
        if (limitMessages.size >= limitMessageCount) view.hideUserInput()
    }
}