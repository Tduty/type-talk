package info.tduty.typetalk.data.entity

import androidx.room.Embedded
import androidx.room.Relation

class LessonWithTask {

    @Embedded
    var lesson: LessonEntity? = null

    @Relation(
        parentColumn = "id",
        entity = TaskEntity::class,
        entityColumn = "lessons_id"
    )
    var tasks: List<TaskEntity>? = null
}