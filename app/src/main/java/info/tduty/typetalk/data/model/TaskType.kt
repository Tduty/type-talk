package info.tduty.typetalk.data.model

import androidx.annotation.DrawableRes
import info.tduty.typetalk.R

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */
enum class TaskType(@DrawableRes val imageSrc: Int) {

    FLASHCARDS(R.drawable.ic_task_flashcards),
    WORDAMESS(R.drawable.ic_task_flashcards),
    HURRY_UP(R.drawable.ic_task_flashcards),
    PHRASE_BUILDING(R.drawable.ic_task_flashcards),
    TRANSLATION(R.drawable.ic_task_flashcards),
    DICTIONARY_PICTIONARY(R.drawable.ic_task_flashcards),
}