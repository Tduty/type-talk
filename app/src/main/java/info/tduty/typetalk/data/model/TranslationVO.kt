package info.tduty.typetalk.data.model

class TranslationVO(
    val type: String,
    val word: String,
    val currentTranslation: String,
    var inputWord: String?,
    typeTask: TaskType?
) : TaskPayloadVO(typeTask)