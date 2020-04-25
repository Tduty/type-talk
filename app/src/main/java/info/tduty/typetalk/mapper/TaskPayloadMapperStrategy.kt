package info.tduty.typetalk.mapper

import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.mapper.task.*


class TaskPayloadMapperStrategy {

    fun getTaskPayloadMapper(type: TaskType): TaskPayloadMapper? {
        return when(type) {
            TaskType.FLASHCARDS -> FlashcardPayloadMapper()
            TaskType.WORDAMESS -> WordamessPayloadMapper()
            TaskType.HURRY_UP -> HurryUpPayloadMapper()
            TaskType.PHRASE_BUILDING -> PhraseBuildingPayloadMapper()
            TaskType.TRANSLATION -> TranslationPayloadMapper()
            TaskType.DICTIONARY_PICTIONARY -> DictionaryPictionaryMapper()
            TaskType.EMPTY -> null
        }
    }
}