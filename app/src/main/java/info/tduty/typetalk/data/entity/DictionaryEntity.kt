package info.tduty.typetalk.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "dictionary",
    foreignKeys = [ForeignKey(
        entity = LessonEntity::class,
        parentColumns = ["id"],
        childColumns = ["lesson_id"],
        onDelete = ForeignKey.NO_ACTION
    )]
)
class DictionaryEntity(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var word: String,
    var translation: String,
    var transcription: String?,
    @ColumnInfo(name = "lesson_id") val lessonsId: Long?
)