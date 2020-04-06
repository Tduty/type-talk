package info.tduty.typetalk.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class MessageEntity(
    @ColumnInfo(name = "id", index = true)
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "sync_id")
    var syncId: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "content")
    var content: String,

    @ColumnInfo(name = "avatar_url")
    var avatarURL: String,

    @ColumnInfo(name = "is_my")
    var isMy: Boolean,

    @ColumnInfo(name = "chat_id", index = true)
    var chatId: String?,

    @ColumnInfo(name = "time")
    var sendingTime: Long
)
