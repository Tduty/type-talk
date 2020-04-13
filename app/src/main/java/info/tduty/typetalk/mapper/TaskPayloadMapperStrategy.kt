package info.tduty.typetalk.mapper

import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.mapper.task.FlashcardPayloadMapper
import info.tduty.typetalk.mapper.task.PhraseBuildingPayloadMapper
import info.tduty.typetalk.mapper.task.WordamessPayloadMapper
import info.tduty.typetalk.mapper.task.TranslationPayloadMapper


class TaskPayloadMapperStrategy {

    fun getTaskPayloadMapper(type: TaskType): TaskPayloadMapper? {
        return when(type) {
            TaskType.FLASHCARDS -> FlashcardPayloadMapper()
            TaskType.WORDAMESS -> WordamessPayloadMapper()
            TaskType.HURRY_UP -> null
            TaskType.PHRASE_BUILDING -> PhraseBuildingPayloadMapper()
            TaskType.TRANSLATION -> TranslationPayloadMapper()
            TaskType.DICTIONARY_PICTIONARY -> null
            TaskType.EMPTY -> null
        }
    }
}