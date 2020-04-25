package info.tduty.typetalk.view.task.dictionarypicationary

import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.DictionaryPictionaryVO
import info.tduty.typetalk.data.model.TaskPayloadVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.domain.interactor.TaskInteractor
import info.tduty.typetalk.utils.Utils
import info.tduty.typetalk.view.task.StateInputWord
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import kotlin.collections.ArrayList

class DictionaryPictionaryPresenter(
    val view: DictionaryPictionaryView,
    private val taskInteractor: TaskInteractor
) {

    private val BTN_TITLE_COMPLETED: Int = R.string.task_btn_complete
    private var isCompleted = false
    private var task: TaskVO? = null
    private val disposables = CompositeDisposable()
    private lateinit var dictionaryPictionaryList: List<DictionaryPictionaryVO>

    fun onCreate(
        taskVO: TaskVO
    ) {
        this.dictionaryPictionaryList = getDictionaryPictionaryList(taskInteractor.getPayload2(taskVO))

        if (this.dictionaryPictionaryList.isEmpty()) {
            view.showError()
        }

        this.task = taskVO
        view.setupDictionaryPictionary(dictionaryPictionaryList)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getDictionaryPictionaryList(listTaskPayload: List<TaskPayloadVO>): List<DictionaryPictionaryVO> {
        return listTaskPayload as? List<DictionaryPictionaryVO> ?: Collections.emptyList()
    }

    fun onScrollCard() {
        view.setTitleNextButton(R.string.task_screen_translation_btn_skip)
        view.setStateInput(StateInputWord.DEFAULT)
        view.hiddenKeyboard()
    }

    fun onChangeEditText(word: String, currentItem: Int) {
        val dictionaryPictionaryVO = dictionaryPictionaryList[currentItem]

        dictionaryPictionaryVO.inputWord = word

        if (word.isEmpty()) {
            view.setStateInput(StateInputWord.DEFAULT)
            view.setTitleNextButton(R.string.task_screen_translation_btn_skip)
            return
        }

        if (dictionaryPictionaryVO.translates.contains(word)) {
            view.setStateInput(StateInputWord.VALID)
            view.setTitleNextButton(R.string.task_btn_next)
        } else {
            view.setTitleNextButton(R.string.task_screen_translation_btn_skip)
            view.setStateInput(StateInputWord.EDIT)
        }
    }

    fun onClickNext(currentPosition: Int, word: String? = "") {
        if (isCompleted) {
            completeTask()
            return
        }

        if (currentPosition == dictionaryPictionaryList.size - 1) {
            view.setTitleNextButton(BTN_TITLE_COMPLETED)
            isCompleted = true
            return
        }


        if (currentPosition + 1 < dictionaryPictionaryList.size) {
            view.showWord(currentPosition + 1, true)
        }
    }

    private fun completeTask() {
        val incorrectWords = dictionaryPictionaryList.filter { !it.isSuccess() }
        val countSuccessTask = dictionaryPictionaryList.size - incorrectWords.size
        val countTask = dictionaryPictionaryList.size
        val successCompletedTaskPercent =
            Utils.getSuccessCompletedTaskPercent(countTask, countSuccessTask)

        if (successCompletedTaskPercent >= 50) {
            successfulExecution(incorrectWords)
        } else {
            unsuccessfulExecution(incorrectWords)
        }
    }

    private fun unsuccessfulExecution(incorrectWords: List<DictionaryPictionaryVO>) {
        view.unsuccessComplete(incorrectWords)
    }

    private fun successfulExecution(incorrectWords: List<DictionaryPictionaryVO>) {
        if (incorrectWords.isNotEmpty()) {
            view.successCompletedWithIncorrectWord(incorrectWords)
        } else {
            view.completeTask()
        }
    }

    fun tryAgain() {
        task?.let {
            isCompleted = false
            view.showWord(0, false)
            onCreate(it)
        }
    }
}