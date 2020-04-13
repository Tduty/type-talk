package info.tduty.typetalk.view.task.translation

import info.tduty.typetalk.data.model.TranslationVO

interface TranslationView {

    fun setupTranslations(translationList: List<TranslationVO>)

    fun showWord(position: Int, isAnimated: Boolean)

    fun setTitleNextButton(title: Int)

    fun setStateEditWord(state: StateEditWord)

    fun setValueToInput(value: String)

    fun showError()

    fun hiddenKeyboard()

    fun completeTask()

    enum class StateEditWord {
        DEFAULT,
        VALID,
        EDIT,
        ERROR
    }
}