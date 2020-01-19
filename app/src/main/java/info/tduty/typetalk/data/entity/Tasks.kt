package info.tduty.typetalk.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "tasks",
        primaryKeys = [ "lesson_id", "task_id" ],
        foreignKeys = [
            ForeignKey(
                entity = LessonEntity::class,
                        parentColumns = ["id"],
                childColumns = ["lesson_id"]
            ),
            ForeignKey(
                entity = TaskEntity::class,
                        parentColumns = ["id"],
                childColumns = ["task_id"]
            )]
        )
data class Tasks(
    @ColumnInfo(name = "task_id") var taskId: Long?,
    @ColumnInfo(name = "lesson_id") var lessonId: Long?
)