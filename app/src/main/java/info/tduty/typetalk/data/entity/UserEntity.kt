package info.tduty.typetalk.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "user")
data class UserEntity(
    var id: Long,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "surname") var surname: String?,
    @ColumnInfo(name = "date_of_birth") var dateOfBirth: Long?,
    @ColumnInfo(name = "grade_id") var gradeId: Long?
)