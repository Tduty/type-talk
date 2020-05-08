package info.tduty.typetalk.data.model

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
data class WordamessVO(
    val isMistake: Boolean,
    val body: String,
    val correctBody: String?,
    var inputText: String = "",
    var isCorrected: Boolean = false,
    var isSkipped: Boolean = false
): TaskPayloadVO(TaskType.WORDAMESS) {

    fun isValid(): Boolean {
         return !isSkipped && isCorrected && inputText == correctBody
    }
}
