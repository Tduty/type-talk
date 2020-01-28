package info.tduty.typetalk.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "lessons",
    foreignKeys = [ForeignKey(
        entity = AccountEntity::class,
        parentColumns = ["id"],
        childColumns = ["account_id"],
        onDelete = ForeignKey.CASCADE
        )]
)
data class LessonEntity(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var title: String?,
    var description: String?,
    @ColumnInfo(name = "is_preformed") var isPerformed: Boolean?,
    @ColumnInfo(name = "account_id") var accountId: Long?
)