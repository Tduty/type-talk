package info.tduty.typetalk.view.task.wordamess

import info.tduty.typetalk.data.model.WordamessVO

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
interface WordamessView {

    fun setupTasks(list: List<WordamessVO>)

    fun setCorrectWordCount(count: Int)

    fun startCorrectWords(list: List<WordamessVO>)

    fun setClickableBtn(isClickable: Boolean)

    fun showErrorExistCorrectWords()

    fun showErrorTasksEmpty()
}