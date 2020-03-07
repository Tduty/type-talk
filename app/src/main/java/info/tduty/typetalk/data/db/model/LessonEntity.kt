package info.tduty.typetalk.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import info.tduty.typetalk.utils.StringList

@Entity(tableName = "lessons")
data class LessonEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "lesson_id")
    var lessonId: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "status")
    var status: Int,

    @ColumnInfo(name = "expected")
    var expectedList: StringList
) {

    companion object {
        const val AWAITING_STATE = 0
        const val CONFIRMED_STATE = 1
    }
}
