package info.tduty.typetalk.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
data class LessonDTO(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("icon")
    val icon: String,

    @SerializedName("status")
    val status: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("expected")
    val expected: List<ExpectedDTO>,

    @SerializedName("tasks")
    val taskDTOList: List<TaskDTO>
) {

    data class ExpectedDTO(
        @SerializedName("icon")
        val icon: String,

        @SerializedName("title")
        val title: String
    )
}

