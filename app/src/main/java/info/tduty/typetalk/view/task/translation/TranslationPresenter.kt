package info.tduty.typetalk.view.task.translation

import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.TaskPayloadVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.data.model.TranslationVO
import info.tduty.typetalk.domain.interactor.ChatInteractor
import info.tduty.typetalk.domain.interactor.HistoryInteractor
import info.tduty.typetalk.domain.interactor.TaskInteractor
import info.tduty.typetalk.view.task.StateInputWord
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*

class TranslationPresenter(
    val view: TranslationView,
    private val taskInteractor: TaskInteractor,
    private val chatInteractor: ChatInteractor,
    private val historyInteractor: HistoryInteractor
) {

    private val disposables = CompositeDisposable()
    private lateinit var translationList: List<TranslationVO>
    private var task: TaskVO? = null
    private var isCompleted = false

    private val BTN_TITLE_SEND_TO_TEACHER = R.string.task_btn_send_to_teacher
    private val BTN_TITLE_NEXT = R.string.task_btn_next
    private val BTN_TITLE_COMPLETED = R.string.task_btn_complete
    private val BTN_TITLE_SKIP = R.string.task_screen_translation_btn_skip

    fun onCreate(
        taskVO: TaskVO
    ) {

        this.translationList = getTranslationList(taskInteractor.getPayload2(taskVO))

        if (this.translationList.isEmpty()) {
            view.showError()
        }

        this.task = taskVO

        view.setupTranslations(translationList)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getTranslationList(listTaskPayload: List<TaskPayloadVO>): List<TranslationVO> {
        return listTaskPayload as? List<TranslationVO> ?: Collections.emptyList()
    }

    fun onScrollCard(position: Int) {
        setNextButtonTitle(position)
        val translationVO = translationList[position]
        if (translationVO.inputWord != null &&
            translationVO.inputWord == translationVO.currentTranslation
        ) {
            view.setValueToInput(translationVO.inputWord!!)
            view.setStateEditWord(StateInputWord.VALID)
            view.setTitleNextButton(BTN_TITLE_NEXT)
        } else {
            view.setStateEditWord(StateInputWord.DEFAULT)
        }
        view.hiddenKeyboard()
    }

    fun onChangeEditText(word: String, currentItem: Int) {
        val translationVO = translationList[currentItem]

        if (word.isEmpty()) {
            view.setStateEditWord(StateInputWord.DEFAULT)
            setNextButtonTitle(currentItem)
            return
        }
        when (translationVO.type) {
            "phrase" -> {
                if (word == translationVO.currentTranslation) {
                    translationVO.inputWord = word
                    view.setStateEditWord(StateInputWord.VALID)
                    view.setTitleNextButton(BTN_TITLE_NEXT)
                } else {
                    view.setStateEditWord(StateInputWord.EDIT)
                }
            }
            "sentence" -> {
                view.setStateEditWord(StateInputWord.EDIT)
            }
        }
    }

    private fun getMessageForTeacher(word: String, content: String): String {
        return """
                    Task: ${task?.title}
                    Lessons id: ${task?.lessonId}
                    Input word: $word
                    Task content: $content
                """.trimIndent()
    }

    fun onClickNext(currentPosition: Int, word: String? = "") {
        val translationVO = translationList[currentPosition]

        if (isCompleted) {
            view.completeTask()
            return
        }

        if (currentPosition == translationList.size - 1) {
            view.setTitleNextButton(BTN_TITLE_COMPLETED)
            isCompleted = true
            return
        }

        when (translationVO.type) {
            "phrase" -> {
                if (currentPosition + 1 < translationList.size) {
                    view.showWord(currentPosition + 1, true)
                }
            }
            "sentence" -> {
                view.setTitleNextButton(BTN_TITLE_SEND_TO_TEACHER)
                if (word != null && word.isNotEmpty()) {
                    translationVO.inputWord = word
                    val message = getMessageForTeacher(word, translationVO.word)
                    sendMessageToTeacherChat(message)
                }
            }
        }
    }

    private fun setNextButtonTitle(position: Int) {
        val translationVO = translationList[position]

        when (translationVO.type) {
            "phrase" -> {
                view.setTitleNextButton(BTN_TITLE_SKIP)
            }
            "sentence" -> {
                view.setTitleNextButton(BTN_TITLE_SEND_TO_TEACHER)
            }
        }
    }

    private fun sendMessageToTeacherChat(message: String) {
        disposables.add(
            chatInteractor.getTeacherChat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ chat ->
                    chat?.let {
                        historyInteractor.sendMessage(chat.chatId, message)
                    }
                }, Timber::e)
        )
    }


    fun onDestroy() {
        disposables.dispose()
    }
}