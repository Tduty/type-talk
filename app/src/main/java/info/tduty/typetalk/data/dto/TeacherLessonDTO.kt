package info.tduty.typetalk.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
data class TeacherLessonDTO(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("icon")
    val icon: String,

    @SerializedName("completed")
    val completed: Int,

    @SerializedName("student_count")
    val studentCount: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("lock")
    val isLock: Boolean
)
