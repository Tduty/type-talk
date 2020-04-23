package info.tduty.typetalk.domain.mapper

import android.content.Context
import info.tduty.typetalk.R
import info.tduty.typetalk.data.db.model.ChatEntity
import info.tduty.typetalk.data.dto.ChatDTO
import info.tduty.typetalk.data.model.ChatVO
import info.tduty.typetalk.data.pref.UserDataHelper

/**
 * Created by Evgeniy Mezentsev on 23.04.2020.
 */
class ChatMapper(
    private val context: Context,
    private val userDataHelper: UserDataHelper
) {

    fun toVO(db: ChatEntity): ChatVO {
        return ChatVO(
            chatId = db.chatId,
            title = getTitle(db.type, db.title),
            type = db.type,
            avatarURL = getIconByType(db.type),
            description = db.description,
            isTeacherChat = isTeacherChat(db.type)
        )
    }

    fun toVO(dto: ChatDTO): ChatVO {
        return ChatVO(
            chatId = dto.id,
            title = getTitle(dto.type, dto.title),
            type = dto.type,
            avatarURL = getIconByType(dto.type),
            description = dto.description ?: "",
            isTeacherChat = isTeacherChat(dto.type)
        )
    }

    fun toDB(dto: ChatDTO): ChatEntity {
        return ChatEntity(
            chatId = dto.id,
            title = dto.title,
            type = dto.type,
            imageURL = dto.icon,
            description = dto.description ?: ""
        )
    }

    private fun isTeacherChat(type: String): Boolean {
        return if (userDataHelper.isTeacher()) false
        else type == ChatEntity.TEACHER_CHAT
    }

    private fun getIconByType(type: String): Int {
        return when (type) {
            ChatEntity.TEACHER_CHAT -> R.drawable.ic_teacher_bg_varden
            ChatEntity.CLASS_CHAT -> R.drawable.ic_class_bg_varden
            ChatEntity.TASK_CHAT -> R.drawable.ic_unknown
            else -> R.drawable.ic_boy_bg_varden
        }
    }

    private fun getTitle(type: String, title: String): String {
        return if (type == ChatEntity.TASK_CHAT) context.getString(R.string.chat_title_dialog)
        else title
    }
}
