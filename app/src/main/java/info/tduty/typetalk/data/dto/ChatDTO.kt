package info.tduty.typetalk.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Evgeniy Mezentsev on 08.03.2020.
 */
data class ChatDTO(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("icon")
    val icon: String?,

    @SerializedName("type")
    val type: String,

    @SerializedName("description")
    val description: String?
)