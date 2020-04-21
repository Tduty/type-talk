package info.tduty.typetalk.data.event.payload

import com.google.gson.annotations.SerializedName
import info.tduty.typetalk.data.event.EventPayload

/**
 * Created by Evgeniy Mezentsev on 21.04.2020.
 */
class CorrectionPayload(
    @SerializedName("sync_id") val syncId: String,
    @SerializedName("chat_id") val chatId: String?,
    @SerializedName("additional_type") val additionalType: Int,
    @SerializedName("additional") val additional: String
) : EventPayload
