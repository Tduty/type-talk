package info.tduty.typetalk.data.event.payload

import com.google.gson.annotations.SerializedName
import info.tduty.typetalk.data.event.EventPayload

class CompleteTaskPayload(
    @SerializedName("lesson_id") val lessonId: String,
    @SerializedName("task_id") val taskId: String,
    @SerializedName("completed") val isCompleted: Boolean
) : EventPayload
