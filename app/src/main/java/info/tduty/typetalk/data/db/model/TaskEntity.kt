package info.tduty.typetalk.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "task", indices = [
    Index(value = ["task_id"], unique = true)
])
data class TaskEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "task_id")
    var taskId: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "type")
    var type: String,

    @ColumnInfo(name = "icon_url")
    var iconUrl: String?,

    @ColumnInfo(name = "is_preformed")
    var isPerformed: Boolean,

    @ColumnInfo(name = "optional")
    var optional: Boolean,

    @ColumnInfo(name = "payload")
    var payload: String?,

    @ColumnInfo(name = "lessons_id")
    var lessonsId: String
)
