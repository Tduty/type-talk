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

    @SerializedName("type")
    val type: String,

    @SerializedName("position")
    val position: Int,

    @SerializedName("status")
    val status: Int,

    @SerializedName("optional")
    val optional: Boolean,

    @SerializedName("payload")
    val payload: String?
)
