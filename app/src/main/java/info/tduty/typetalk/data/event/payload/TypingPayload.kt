package info.tduty.typetalk.data.event.payload

import com.google.gson.annotations.SerializedName
import info.tduty.typetalk.data.event.EventPayload


/**
 * Created by Evgeniy Mezentsev on 04.03.2020.
 */
data class TypingPayload(
    @SerializedName("sender_id")
    val senderId: String? = null,
    @SerializedName("chat_id")
    val chatId: String? = null,
    @SerializedName("type")
    val type: String? = null
) : EventPayload
