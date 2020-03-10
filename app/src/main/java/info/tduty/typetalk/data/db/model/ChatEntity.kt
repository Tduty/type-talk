package info.tduty.typetalk.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat")
data class ChatEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "chat_id")
    var chatId: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "image_url")
    var imageURL: String?,

    @ColumnInfo(name = "description")
    var description: String
)