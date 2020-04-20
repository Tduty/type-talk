package info.tduty.typetalk.data.event.payload

import com.google.gson.annotations.SerializedName
import info.tduty.typetalk.data.event.EventPayload

/**
 * Created by Evgeniy Mezentsev on 19.04.2020.
 */
data class UserStatusPayload(
    @SerializedName("user_id") val userId: String,
    @SerializedName("status") val status: String
) : EventPayload