package info.tduty.typetalk.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "grade")
data class GradeEntity(
    var id: Long?,
    var name: String?,
    var letter: String?,
    @ColumnInfo(name = "count_student") var countStudent: Int?
)