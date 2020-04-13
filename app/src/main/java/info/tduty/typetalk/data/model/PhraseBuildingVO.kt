package info.tduty.typetalk.data.model

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
data class PhraseBuildingVO(
    val id: String,
    val phrases: List<String>,
    var buildingText: String = "",
    var isCorrectText: Boolean = false
): TaskPayloadVO(TaskType.PHRASE_BUILDING)
