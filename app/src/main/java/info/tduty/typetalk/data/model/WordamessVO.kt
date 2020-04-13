package info.tduty.typetalk.data.model

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
data class WordamessVO(
    val isMistake: Boolean,
    val body: String,
    val correctBody: String?
): TaskPayloadVO(TaskType.WORDAMESS)
