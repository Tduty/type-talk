package info.tduty.typetalk.view.task.dictionarypicationary

import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.DictionaryPictionaryVO
import info.tduty.typetalk.data.model.TaskPayloadVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.domain.interactor.TaskInteractor
import info.tduty.typetalk.view.task.StateInputWord
import io.reactivex.disposables.CompositeDisposable
import java.util.*

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

        if (word.isEmpty()) {
            view.setStateInput(StateInputWord.DEFAULT)
            view.setTitleNextButton(R.string.task_screen_translation_btn_skip)
            return
        }

        if (dictionaryPictionaryVO.translates.contains(word)) {
            dictionaryPictionaryVO.inputWord = word
            view.setStateInput(StateInputWord.VALID)
            view.setTitleNextButton(R.string.task_btn_next)
        } else {
            view.setTitleNextButton(R.string.task_screen_translation_btn_skip)
            view.setStateInput(StateInputWord.EDIT)
        }

    }

    fun onClickNext(currentPosition: Int, word: String? = "") {
        if (isCompleted) {
            view.completeTask()
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
}