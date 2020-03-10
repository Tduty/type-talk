package info.tduty.typetalk.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import info.tduty.typetalk.data.db.model.LessonEntity

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
    @ColumnInfo(name = "id", index = true)
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "icon_url") var iconUrl: String?,
    @ColumnInfo(name = "is_preformed") var isPerformed: Boolean?,
    @ColumnInfo(name = "lessons_id", index = true) var lessonsId: Long?
)
