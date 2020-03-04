package info.tduty.typetalk.data.event.payload

import com.google.gson.annotations.SerializedName
import info.tduty.typetalk.data.event.EventPayload


/**
 * Created by Evgeniy Mezentsev on 04.03.2020.
 */
class MessageNewPayload(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("chat_id")
    val chatId: String? = null,
    @SerializedName("sender_id")
    val senderId: String? = null,
    @SerializedName("body")
    val body: String? = null,
    @SerializedName("sending_time")
    val sendingTime: Long? = null,
    @SerializedName("exist_mistake")
    val existMistake: Boolean? = null
) : EventPayload
