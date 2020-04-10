package info.tduty.typetalk.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary", indices = [
    Index(value = ["dictionary_id"], unique = true)
])
data class DictionaryEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "dictionary_id")
    var dictionaryId: String,

    @ColumnInfo(name = "word")
    var word: String,

    @ColumnInfo(name = "translation")
    var translation: String,

    @ColumnInfo(name = "transcription")
    var transcription: String?,

    @ColumnInfo(name = "lesson_id")
    val lessonsId: String
)
