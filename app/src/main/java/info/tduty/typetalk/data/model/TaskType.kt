package info.tduty.typetalk.data.model

import androidx.annotation.DrawableRes
import info.tduty.typetalk.R
import info.tduty.typetalk.data.db.model.TaskEntity

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */
enum class TaskType(@DrawableRes val imageSrc: Int) {

    FLASHCARDS(R.drawable.ic_flashcard),
    WORDAMESS(R.drawable.ic_wordamess),
    HURRY_UP(R.drawable.ic_hurry_up),
    PHRASE_BUILDING(R.drawable.ic_phrase_building),
    TRANSLATION(R.drawable.ic_translation),
    DICTIONARY_PICTIONARY(R.drawable.ic_pictionary),
    EMPTY(0);

    companion object {
        fun fromDbType(type: String): TaskType {
            return when (type) {
                TaskEntity.FLASHCARDS_TYPE -> FLASHCARDS
                TaskEntity.WORDAMESS_TYPE -> WORDAMESS
                TaskEntity.HURRY_UP_TYPE -> HURRY_UP
                TaskEntity.PHRASE_BUILDING__TYPE -> PHRASE_BUILDING
                TaskEntity.TRANSLATION_TYPE -> TRANSLATION
                TaskEntity.DICTIONARY_PICTIONARY_TYPE -> DICTIONARY_PICTIONARY
                else -> EMPTY
            }
        }
    }
}