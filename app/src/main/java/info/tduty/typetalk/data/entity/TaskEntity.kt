package info.tduty.typetalk.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "task",
    foreignKeys = [ForeignKey(
        entity = LessonEntity::class,
        parentColumns = ["id"],
        childColumns = ["lessons_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var title: String?,
    var description: String?,
    @ColumnInfo(name = "icon_url") var iconUrl: String?,
    @ColumnInfo(name = "is_preformed") var isPerformed: Boolean?,
    @ColumnInfo(name = "lessons_id") var lessonsId: Long?
)