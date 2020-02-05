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
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "word") var word: String,
    @ColumnInfo(name = "translation") var translation: String,
    @ColumnInfo(name = "transcription") var transcription: String?,
    @ColumnInfo(name = "lesson_id") val lessonsId: Long?
)
