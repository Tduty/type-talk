package info.tduty.typetalk.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grade")
data class GradeEntity(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var number: Int,
    var letter: String,
    @ColumnInfo(name = "count_student") var countStudent: Int?
)