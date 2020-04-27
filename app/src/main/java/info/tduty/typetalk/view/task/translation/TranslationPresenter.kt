package info.tduty.typetalk.view.task.translation

import info.tduty.typetalk.R
import info.tduty.typetalk.data.event.payload.CompleteTaskPayload
import info.tduty.typetalk.data.model.TaskPayloadVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.data.model.TranslationVO
import info.tduty.typetalk.domain.interactor.ChatInteractor
import info.tduty.typetalk.domain.interactor.HistoryInteractor
import info.tduty.typetalk.domain.interactor.LessonInteractor
import info.tduty.typetalk.domain.interactor.TaskInteractor
import info.tduty.typetalk.socket.SocketController
import info.tduty.typetalk.utils.Utils
import info.tduty.typetalk.view.task.StateInputWord
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*

class TranslationPresenter(
    val view: TranslationView,
    private val taskInteractor: TaskInteractor,
    private val lessonsInteractor: LessonInteractor,
    private val chatInteractor: ChatInteractor,
    private val historyInteractor: HistoryInteractor,
    private val socketController: SocketController
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
            translationVO.currentTranslation.isNotEmpty() &&
            translationVO.inputWord == translationVO.currentTranslation
        ) {
            view.setValueToInput(translationVO.inputWord!!)
            view.setStateEditWord(StateInputWord.VALID)
            view.setTitleNextButton(BTN_TITLE_NEXT)
        } else {
            view.setStateEditWord(StateInputWord.DEFAULT)
        }
    }

    fun onChangeEditText(word: String, currentItem: Int) {
        val translationVO = translationList[currentItem]

        translationVO.inputWord = word

        if (word.isEmpty()) {
            view.setStateEditWord(StateInputWord.DEFAULT)
            setNextButtonTitle(currentItem)
            return
        }

        when (translationVO.type) {
            TranslationVO.PHRASE_TYPE -> {
                if (word == translationVO.currentTranslation) {
                    view.setStateEditWord(StateInputWord.VALID)
                    view.setTitleNextButton(BTN_TITLE_NEXT)
                } else {
                    view.setStateEditWord(StateInputWord.EDIT)
                }
            }
            TranslationVO.SENTENCE_TYPE -> {
                view.setStateEditWord(StateInputWord.EDIT)
            }
        }
    }

    private fun getMessageForTeacher(word: String, content: String, lessonTitle: String): String {
        return """
                    Task: ${task?.title}
                    Lessons id: $lessonTitle
                    Input word: $word
                    Task content: $content
                """.trimIndent()
    }

    fun onClickNext(currentPosition: Int, word: String? = "") {
        val translationVO = translationList[currentPosition]

        if (currentPosition == translationList.size - 1) {
            view.setTitleNextButton(BTN_TITLE_COMPLETED)
            isCompleted = true
            completeTask()
            return
        }

        when (translationVO.type) {
            TranslationVO.PHRASE_TYPE -> {
                if (currentPosition + 1 < translationList.size) {
                    view.hiddenKeyboard()
                    view.showWord(currentPosition + 1, true)
                }
            }
            TranslationVO.SENTENCE_TYPE -> {
                view.setTitleNextButton(BTN_TITLE_SEND_TO_TEACHER)
                if (word != null) {
                    translationVO.inputWord = word
                    task?.let {
                        createMessage(it, word, translationVO.word)
                    }
                    view.showWord(currentPosition + 1, true)
                }
            }
        }
    }

    private fun completeTask() {
        val countSuccessTask =
            translationList.filter { it.currentTranslation == it.inputWord && it.type != TranslationVO.SENTENCE_TYPE }
                .size
        val countTask = translationList.filter { it.type != TranslationVO.SENTENCE_TYPE }.size
        val successCompletedTaskPercent =
            Utils.getSuccessCompletedTaskPercent(countTask, countSuccessTask)

        val incorrectWords = translationList.filter { it.currentTranslation != it.inputWord }

        if (successCompletedTaskPercent >= 50) {
            successfulExecution(incorrectWords)
        } else {
            unsuccessfulExecution(incorrectWords)
        }
    }

    private fun successfulExecution(incorrectWord: List<TranslationVO>) {
        if (incorrectWord.isNotEmpty()) {
            view.successCompletedWithIncorrectWord(incorrectWord)
        } else {
            view.completeTask()
        }
    }

    private fun unsuccessfulExecution(incorrectWord: List<TranslationVO>) {
        view.unsuccessComplete(incorrectWord)
    }

    private fun createMessage(taskVO: TaskVO, word: String, validTranslation: String) {
        disposables.add(
            lessonsInteractor.getLesson(taskVO.lessonId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ lesson ->
                    lesson?.let {
                        val message = getMessageForTeacher(word, validTranslation, lesson.title)
                        sendMessageToTeacherChat(message)
                    }
                }, Timber::e)
        )
    }

    private fun setNextButtonTitle(position: Int) {
        val translationVO = translationList[position]

        when (translationVO.type) {
            TranslationVO.PHRASE_TYPE -> {
                view.setTitleNextButton(BTN_TITLE_SKIP)
            }
            TranslationVO.SENTENCE_TYPE -> {
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

    fun tryAgain() {
        task?.let {
            isCompleted = false
            view.showWord(0, false)
            onCreate(it)
        }
    }

    fun sendEventCompleteTask() {
        socketController.sendCompleteTask(
            CompleteTaskPayload(
                task?.lessonId ?: "",
                task?.id ?: "",
                true
            )
        )
    }
}