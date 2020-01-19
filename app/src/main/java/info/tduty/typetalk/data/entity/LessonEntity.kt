package info.tduty.typetalk.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "lessons")
data class LessonEntity (
    var id: Long?,
    var title: String?,
    var description: String?,
    @ColumnInfo(name = "is_preformed") var isPerformed: Boolean?
)