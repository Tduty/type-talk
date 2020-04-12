package info.tduty.typetalk.data.model

class FlashcardVO(
    val type: String,
    val frontWord: String,
    val backWord: String,
    taskType: TaskType?
) : TaskPayloadVO(taskType)
