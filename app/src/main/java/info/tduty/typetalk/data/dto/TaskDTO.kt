package info.tduty.typetalk.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
data class TaskDTO(
    @SerializedName("id")
    val id: String,

    @SerializedName("icon")
    val icon: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("status")
    val status: Int
)
