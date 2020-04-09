package info.tduty.typetalk.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
data class MessageDTO(
    @SerializedName("id")
    val id: String,

    @SerializedName("chat_id")
    val chatId: String,

    @SerializedName("sender_id")
    val senderId: String,

    @SerializedName("sender_name")
    val senderName: String,

    @SerializedName("sender_type")
    val senderType: String,

    @SerializedName("body")
    val body: String,

    @SerializedName("sending_time")
    val sendingTime: Long, //TODO: перейти к timestamp

    @SerializedName("exist_mistake")
    val existMistake: Boolean
)
