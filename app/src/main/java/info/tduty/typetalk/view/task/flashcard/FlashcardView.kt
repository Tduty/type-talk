package info.tduty.typetalk.view.task.flashcard

import info.tduty.typetalk.data.model.FlashcardVO

interface FlashcardView {

    fun nextFlashcard(flashcard: FlashcardVO)
}