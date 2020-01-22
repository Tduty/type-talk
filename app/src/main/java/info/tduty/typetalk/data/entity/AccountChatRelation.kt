package info.tduty.typetalk.data.entity

import androidx.room.Entity

@Entity(primaryKeys = ["accountId", "chatId"])
data class AccountChatRelation(
    val accountId: Long,
    val chatId: Long
)