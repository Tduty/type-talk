package info.tduty.typetalk.data.model

import androidx.annotation.DrawableRes
import info.tduty.typetalk.R

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
}