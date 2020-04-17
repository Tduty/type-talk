package info.tduty.typetalk.data.model

class HurryUpVO(
    val word: String,
    val type: String,
    val translate: String,
    val isComplete: Boolean,
    val anyTranslates: List<String>
) : TaskPayloadVO(TaskType.HURRY_UP)