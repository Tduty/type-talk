package info.tduty.typetalk.data.event.payload

import com.google.gson.annotations.SerializedName
import info.tduty.typetalk.data.event.EventPayload


/**
 * Created by Evgeniy Mezentsev on 04.03.2020.
 */
class MessageNewPayload(
    @SerializedName("id")
    val id: String,
    @SerializedName("chat_id")
    val chatId: String,
    @SerializedName("sender_id")
    val senderId: String,
    @SerializedName("sender_name")
    val senderName: String,
    @SerializedName("sender_type")
    val senderType: String? = null,
    @SerializedName("body")
    val body: String,
    @SerializedName("sending_time")
    val sendingTime: Long = 0L,
    @SerializedName("exist_mistake")
    val existMistake: Boolean = false
) : EventPayload
