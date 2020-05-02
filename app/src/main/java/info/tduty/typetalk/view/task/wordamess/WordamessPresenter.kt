package info.tduty.typetalk.view.task.wordamess

import info.tduty.typetalk.data.event.payload.CompleteTaskPayload
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.data.model.WordamessVO
import info.tduty.typetalk.domain.interactor.TaskInteractor
import info.tduty.typetalk.socket.SocketController
import info.tduty.typetalk.utils.Utils

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
class WordamessPresenter(
    private val view: WordamessView,
    private val taskInteractor: TaskInteractor,
    private val socketController: SocketController
) {

    private var taskVO: TaskVO? = null
    private lateinit var lessonId: String
    private lateinit var tasks: MutableMap<String, WordamessVO>
    private var chooseList: List<WordamessVO> = emptyList()
    private var correctList: List<WordamessVO> = emptyList()
    private val selectedTasks = hashMapOf<String, WordamessVO>()

    fun onCreate(taskVO: TaskVO) {
        this.taskVO = taskVO
        lessonId = taskVO.lessonId
        val tasks = taskInteractor.getPayload2(taskVO) as? List<WordamessVO> ?: emptyList()
        this.tasks = tasks.shuffled().subList(0, 20).map { it.body to it }.toMap().toMutableMap()
        setupChosenBlock()
    }

    private fun setupChosenBlock() {
        correctList.forEach { tasks.remove(it.body) }
        chooseList = tasks.values.shuffled()
        view.setupTasksForChoose(chooseList)
        view.setClickableBtn(false)
        view.setCorrectWordCount(0)
        view.showChosenBlock()
    }

    private fun setupCorrectBlock() {
        correctList = selectedTasks.values.shuffled()
        selectedTasks.clear()
        view.setupTasksForCorrect(correctList)
        view.showWordForCorrect(0)
        view.setStateEditWord(WordamessView.StateEditWord.DEFAULT)
        view.setValueToInput("")
        view.showCorrectBlock()
    }

    fun selectWord(body: String) {
        if (selectedTasks.contains(body)) return
        view.setClickableBtn(true)
        selectedTasks[body] = tasks[body] ?: return
        view.setCorrectWordCount(selectedTasks.size)
    }

    fun deselectWord(body: String) {
        if (!selectedTasks.contains(body)) return
        selectedTasks.remove(body)
        if (selectedTasks.isEmpty()) view.setClickableBtn(false)
        view.setCorrectWordCount(selectedTasks.size)
    }

    fun onCorrect() {
        if (selectedTasks.all { it.value.isMistake }) {
            setupCorrectBlock()
        } else view.showErrorExistCorrectWords()
    }

    fun onScrollCard(position: Int) {
        val wordamess = correctList[position]
        if (wordamess.inputText == wordamess.correctBody) {
            wordamess.isCorrected = true
            setNextButtonTitle(position, true)
            view.setValueToInput(wordamess.inputText)
            view.setStateEditWord(WordamessView.StateEditWord.VALID)
        } else {
            setNextButtonTitle(position)
            view.setValueToInput(wordamess.inputText)
            view.setStateEditWord(WordamessView.StateEditWord.DEFAULT)
        }
    }

    fun onChangeEditText(word: String, currentItem: Int) {
        val wordamess = correctList[currentItem]

        if (word.isEmpty()) {
            wordamess.inputText = ""
            view.setStateEditWord(WordamessView.StateEditWord.DEFAULT)
            setNextButtonTitle(currentItem)
            return
        }
        wordamess.inputText = word
        if (word == wordamess.correctBody) {
            wordamess.isCorrected = true
            view.setStateEditWord(WordamessView.StateEditWord.VALID)
            setNextButtonTitle(currentItem, true)
        } else {
            wordamess.isCorrected = false
            view.setStateEditWord(WordamessView.StateEditWord.EDIT)
        }
    }

    fun onClickNext(position: Int) {
        if (!correctList[position].isCorrected) {
            correctList[position].isSkipped = true
        }
        when {
            isCompleted(position) -> completeTask()
            position == correctList.lastIndex -> setupChosenBlock()
            else -> {
                view.hiddenKeyboard()
                view.setValueToInput("")
                setNextButtonTitle(position + 1)
                view.showWordForCorrect(position + 1)
            }
        }
    }

    private fun setNextButtonTitle(position: Int, isCorrected: Boolean = false) {
        if (isLastCorrect(position)) {
            view.setupTitleCompletedBtn()
        } else {
            if (isCorrected) view.setupTitleNextBtn()
            else view.setupTitleSkipBtn()
        }
    }

    private fun isCompleted(position: Int): Boolean {
        return isLastCorrect(position)
                && correctList.all { it.isCorrected || it.isSkipped }
    }

    private fun isLastCorrect(position: Int): Boolean {
        return tasks.values.filter { it.isMistake }.size == correctList.size
                && position == correctList.lastIndex
    }

    private fun completeTask() {
        val skippedWord = correctList.filter { it.isSkipped }
        val countSuccessTask = correctList.size - skippedWord.size
        val countWords = correctList.size
        val successCompletedTaskPercent =
            Utils.getSuccessCompletedTaskPercent(countWords, countSuccessTask)

        if (successCompletedTaskPercent >= 50) {
            successfulExecution(skippedWord)
        } else {
            unsuccessfulExecution(skippedWord)
        }
    }

    private fun unsuccessfulExecution(skippedWord: List<WordamessVO>) {
        view.unsuccessComplete(skippedWord)
    }

    private fun successfulExecution(skippedWord: List<WordamessVO>) {
        if (skippedWord.isNotEmpty()) {
            view.successCompletedWithIncorrectWord(skippedWord)
        } else {
            sendEventCompleteTask()
            view.completeTask()
        }
    }

    fun onDestroy() {
    }

    fun tryAgain() {
        taskVO?.let {
            view.showWordForCorrect(0)
            onCreate(it)
        }
    }

    fun sendEventCompleteTask() {
        socketController.sendCompleteTask(
            CompleteTaskPayload(
                taskVO?.lessonId ?: "",
                taskVO?.id ?: "",
                true
            )
        )
    }
}