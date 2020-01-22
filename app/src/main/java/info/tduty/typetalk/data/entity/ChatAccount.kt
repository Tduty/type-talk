package info.tduty.typetalk.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ChatAccount(
    @Embedded val chat: ChatEntity,
    @Relation(
        parentColumn = "chatId",
        entityColumn = "accountId",
        entity = AccountChatRelation::class
    ) val account: List<AccountEntity>
)