package info.tduty.typetalk.data.event

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Evgeniy Mezentsev on 04.03.2020.
 */
data class Event(
    @SerializedName("type") val type: String,
    @Expose(serialize = false, deserialize = false) var eventPayload: EventPayload?
)