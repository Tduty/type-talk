package info.tduty.typetalk.mapper

import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.mapper.task.FlashcardPayloadMapper

class TaskPayloadMapperStrategy {

    fun getTaskPayloadMapper(type: TaskType): TaskPayloadMapper? {
        when(type) {
            TaskType.FLASHCARDS -> {
                return FlashcardPayloadMapper()
            }
            TaskType.WORDAMESS -> TODO()
            TaskType.HURRY_UP -> TODO()
            TaskType.PHRASE_BUILDING -> TODO()
            TaskType.TRANSLATION -> TODO()
            TaskType.DICTIONARY_PICTIONARY -> TODO()
            TaskType.EMPTY -> TODO()
        }
    }
}