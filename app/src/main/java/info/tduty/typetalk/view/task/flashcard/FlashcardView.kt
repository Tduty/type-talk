package info.tduty.typetalk.view.task.flashcard

import info.tduty.typetalk.data.model.FlashcardVO

interface FlashcardView {

    fun setupFlashcards(flashcardVO: List<FlashcardVO>)

    fun showWord(position: Int, isAnimated: Boolean)

    fun showError()

    fun setTitleNextButton(title: Int)

    fun completeTask()

    fun completedWithNext()
    fun disableUI(isDisable: Boolean)
}