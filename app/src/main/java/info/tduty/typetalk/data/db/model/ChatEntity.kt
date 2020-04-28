package info.tduty.typetalk.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "chat", indices = [
    Index(value = ["chat_id"], unique = true)
])
data class ChatEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "chat_id")
    var chatId: String,

    @ColumnInfo(name = "type")
    var type: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "image_url")
    var imageURL: String?,

    @ColumnInfo(name = "description")
    var description: String
) {

    companion object {
        const val TEACHER_CHAT = "teacher_chat"
        const val CLASS_CHAT = "class_chat"
        const val TASK_CHAT = "task"
        const val CHAT = "chat"
    }
}
