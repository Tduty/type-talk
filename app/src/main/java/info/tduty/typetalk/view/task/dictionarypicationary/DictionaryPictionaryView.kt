package info.tduty.typetalk.view.task.dictionarypicationary

import info.tduty.typetalk.data.model.DictionaryPictionaryVO
import info.tduty.typetalk.view.task.StateInputWord

interface DictionaryPictionaryView {

    fun setTitleNextButton(title: Int)

    fun setStateInput(state: StateInputWord)

    fun showError()

    fun hiddenKeyboard()

    fun setupDictionaryPictionary(dictionaryPictionaryList: List<DictionaryPictionaryVO>)

    fun completeTask()

    fun showWord(position: Int, isAnimated: Boolean)
}