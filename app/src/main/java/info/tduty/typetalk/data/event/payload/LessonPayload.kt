package info.tduty.typetalk.data.event.payload

import com.google.gson.annotations.SerializedName
import info.tduty.typetalk.data.event.EventPayload


/**
 * Created by Evgeniy Mezentsev on 04.03.2020.
 */
class LessonPayload(
    @SerializedName("id") val id: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("icon") val icon: String? = null,
    @SerializedName("status") val status: Int = 0,
    @SerializedName("description") val description: String? = null,
    @SerializedName("expected") val expectedList: List<ExpectedPayload>? = null
) : EventPayload {

    inner class ExpectedPayload(
        @SerializedName("icon") var icon: String?,
        @SerializedName("title") var title: String?
    )
}
