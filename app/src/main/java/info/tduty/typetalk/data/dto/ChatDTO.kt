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
    val icon: String,

    @SerializedName("status")
    val type: Int,

    @SerializedName("description")
    val description: String
) {

    companion object {
        const val TEACHER_CHAT = 0
        const val CLASS_CHAT = 1
        const val CLASSMATE_CHAT = 2
        const val BOT_CHAT = 3
    }
}