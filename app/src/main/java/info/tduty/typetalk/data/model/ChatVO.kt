package info.tduty.typetalk.data.model

/**
 * Created by Evgeniy Mezentsev on 08.03.2020.
 */
data class ChatVO(
    val chatId: String,
    val title: String,
    val type: String,
    val avatarURL: Int?,
    val description: String,
    val isTeacherChat: Boolean
)