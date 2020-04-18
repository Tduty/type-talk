package info.tduty.typetalk.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Evgeniy Mezentsev on 19.04.2020.
 */
@Entity(tableName = "student", indices = [
    Index(value = ["student_id"], unique = true)
])
data class StudentEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "student_id")
    var studentId: String,

    @ColumnInfo(name = "chat_id")
    var chatId: String,

    @ColumnInfo(name = "class_id")
    var classId: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "type")
    var type: String,

    @ColumnInfo(name = "status")
    var status: String
) {

    companion object {

        const val MALE_TYPE = "male"
        const val FEMALE_TYPE = "female"

        const val NOT_CONNECTED_STATUS = "not_connected"
        const val DISCONNECTED_STATUS = "disconnected"
        const val CONNECTED_STATUS = "connected"
    }
}