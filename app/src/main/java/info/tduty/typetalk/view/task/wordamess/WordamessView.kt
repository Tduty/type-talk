package info.tduty.typetalk.view.task.wordamess

import info.tduty.typetalk.data.model.WordamessVO
import info.tduty.typetalk.view.task.translation.TranslationView

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
interface WordamessView {

    fun showChosenBlock()

    fun showCorrectBlock()

    fun setupTasksForChoose(list: List<WordamessVO>)

    fun setupTasksForCorrect(list: List<WordamessVO>)

    fun showWordForCorrect(position: Int)

    fun setCorrectWordCount(count: Int)

    fun setupTitleNextBtn()

    fun setupTitleSkipBtn()

    fun setupTitleCompletedBtn()

    fun setStateEditWord(state: StateEditWord)

    fun setValueToInput(value: String)

    fun hiddenKeyboard()

    fun setClickableBtn(isClickable: Boolean)

    fun completeTask(lessonId: String)

    fun showErrorExistCorrectWords()

    fun showErrorTasksEmpty()

    enum class StateEditWord {
        DEFAULT,
        VALID,
        EDIT
    }
}