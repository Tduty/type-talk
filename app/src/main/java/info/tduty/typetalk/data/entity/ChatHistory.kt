package info.tduty.typetalk.data.entity

import androidx.room.Embedded
import androidx.room.Relation


class ChatHistory {

    @Embedded
    var chat: ChatEntity? = null

    @Relation(parentColumn = "id", entity = MessageEntity::class, entityColumn = "chat_id")
    var messages: List<MessageEntity>? = null
}