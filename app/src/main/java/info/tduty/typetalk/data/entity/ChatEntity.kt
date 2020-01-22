package info.tduty.typetalk.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat")
class ChatEntity(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var title: String,
    @ColumnInfo(name = "image_url") var imageURL: String?,
    var description: String
)