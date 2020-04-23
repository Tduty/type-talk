package info.tduty.typetalk.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
data class TeacherTaskDTO(
    @SerializedName("id")
    val id: String,

    @SerializedName("lesson_id")
    val lessonId: String,

    @SerializedName("icon")
    val icon: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("position")
    val position: Int,

    @SerializedName("completed")
    val completed: Int,

    @SerializedName("student_count")
    val studentCount: Int
)
