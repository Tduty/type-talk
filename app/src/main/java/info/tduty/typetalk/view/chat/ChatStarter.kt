package info.tduty.typetalk.view.chat

import android.os.Parcelable
import info.tduty.typetalk.data.model.MessageVO
import kotlinx.android.parcel.Parcelize

/**
 * Created by Evgeniy Mezentsev on 23.04.2020.
 */
@Parcelize
data class ChatStarter(
    val chatId: String? = null,
    val chatType: String,
    val description: String? = descriptionByType(chatType),
    val isActiveInput: Boolean = true,
    val countMessages: Int? = null
) : Parcelable {

    companion object {

        fun descriptionByType(chatType: String?): String? {
            return null
        }
    }

    fun isNotActiveInput() = !isActiveInput

    fun getFirstEvent(): MessageVO? {
        return if (description == null) null
        else MessageVO(
            chatId = chatId ?: "",
            type = MessageVO.Type.EVENT,
            senderName = "server",
            message = description
        )
    }
}
