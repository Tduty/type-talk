package info.tduty.typetalk.domain.mapper

import info.tduty.typetalk.data.model.TaskPayloadVO

interface TaskPayloadMapper {
    fun map(payload: String): List<TaskPayloadVO>
}
