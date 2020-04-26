package info.tduty.typetalk.data.event.payload

import com.google.gson.annotations.SerializedName
import info.tduty.typetalk.data.event.EventPayload

class LessonProgressPayload(
    @SerializedName("lesson_id") val lessonId: String,
    @SerializedName("state") val state: Int? = null
) : EventPayload
