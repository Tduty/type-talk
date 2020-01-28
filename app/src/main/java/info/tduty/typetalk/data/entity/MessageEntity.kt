package info.tduty.typetalk.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
    @PrimaryKey(autoGenerate = true) var id: Long,
    var title: String,
    var content: String,
    @ColumnInfo(name = "avatar_url") var avatarURL: String,
    @ColumnInfo(name = "is_my") var isMy: Boolean,
    @ColumnInfo(name = "chat_id") var chatId: Long?
)