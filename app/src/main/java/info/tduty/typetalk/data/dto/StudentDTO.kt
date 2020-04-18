package info.tduty.typetalk.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
data class StudentDTO(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val name: String,

    @SerializedName("chat_id")
    val chatId: String,

    @SerializedName("class_id")
    val classId: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("status")
    val status: String
)
