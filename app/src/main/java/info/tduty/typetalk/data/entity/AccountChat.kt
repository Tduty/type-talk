package info.tduty.typetalk.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AccountChat(
    @Embedded val account: AccountEntity,
    @Relation(
        parentColumn = "accountId",
        entityColumn = "chatId",
        entity = AccountChatRelation::class
    )
    val chat: List<ChatEntity>
)