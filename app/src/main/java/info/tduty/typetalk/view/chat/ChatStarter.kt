package info.tduty.typetalk.view.chat

import android.os.Parcelable
import info.tduty.typetalk.data.db.model.ChatEntity
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
            //TODO вынести в ресурсы
            return when (chatType) {
                ChatEntity.TEACHER_CHAT -> "Here you can ask your questions and get  all the information you need. Both languages (English and Russian) are available."
                ChatEntity.CLASS_CHAT -> "This  is your class chat. Wait for your teacher’s instructions. Remember, please, here you are supposed to type only in English (Russian keyboard is unavailable). If you need some information or you don’t understand the task click on the teacher’s chat and ask your questions there in any language you wish."
                else -> null
            }
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
