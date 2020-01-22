package info.tduty.typetalk.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user",
    foreignKeys = [androidx.room.ForeignKey(
        entity = GradeEntity::class,
        parentColumns = ["id"],
        childColumns = ["grade_id"],
        onDelete = androidx.room.ForeignKey.NO_ACTION
    )]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "surname") var surname: String?,
    @ColumnInfo(name = "date_of_birth") var dateOfBirth: Long?,
    @ColumnInfo(name = "grade_id") var gradeId: Long?
)