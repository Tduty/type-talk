package info.tduty.typetalk.data.model

import androidx.annotation.DrawableRes

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */
data class MessageVO(
    val id: String,
    val isMy: Boolean,
    val senderName: String,
    @DrawableRes val avatar: Int,
    val message: String
)