package info.tduty.typetalk.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
data class CreateDialogDTO(
    @SerializedName("lesson_id")
    val lessonId: String,

    @SerializedName("task_id")
    val taskId: String,

    @SerializedName("members")
    val members: List<String>
)
