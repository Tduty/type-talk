package info.tduty.typetalk.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
@Entity(
    tableName = "class", indices = [
        Index(value = ["class_id"], unique = true)
    ]
)
data class ClassEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "class_id")
    var classId: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "description")
    var description: String?,

    @ColumnInfo(name = "member_count")
    var memberCount: Int
)
