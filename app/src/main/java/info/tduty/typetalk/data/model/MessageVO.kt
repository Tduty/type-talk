package info.tduty.typetalk.data.model

import androidx.annotation.DrawableRes
import java.util.*

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */
data class MessageVO(
    val id: String = UUID.randomUUID().toString(),
    val chatId: String,
    val type: Type,
    val isMy: Boolean = false,
    val showSender: Boolean = false,
    val senderName: String,
    @DrawableRes val avatar: Int = 0,
    val message: String,
    var correction: CorrectionVO? = null
) {

    enum class Type {
        EVENT,
        MESSAGE
    }
}