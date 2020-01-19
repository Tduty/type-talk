package info.tduty.typetalk.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "task")
data class TaskEntity(
    var id: Long?,
    var title: String?,
    var description: String?,
    @ColumnInfo(name = "icon_url") var iconUrl: String?,
    @ColumnInfo(name = "is_preformed") var isPerformed: Boolean?
)