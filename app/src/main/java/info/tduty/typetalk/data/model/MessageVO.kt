package info.tduty.typetalk.data.model

import androidx.annotation.DrawableRes

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */
data class MessageVO(
    val id: String,
    val chatId: String,
    val type: Type,
    val isMy: Boolean,
    val showSender: Boolean,
    val senderName: String,
    @DrawableRes val avatar: Int,
    val message: String
) {

    enum class Type {
        EVENT,
        MESSAGE
    }
}