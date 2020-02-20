package info.tduty.typetalk.data.entity

import androidx.room.*

@Entity(
    tableName = "message",
    foreignKeys = [ForeignKey(
        entity = ChatEntity::class,
        parentColumns = ["id"],
        childColumns = ["chat_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class MessageEntity(
    @ColumnInfo(name = "id", index = true)
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "content") var content: String,
    @ColumnInfo(name = "avatar_url") var avatarURL: String,
    @ColumnInfo(name = "is_my") var isMy: Boolean,
    @ColumnInfo(name = "chat_id", index = true) var chatId: Long?
)
