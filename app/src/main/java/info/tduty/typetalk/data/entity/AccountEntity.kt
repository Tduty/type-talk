package info.tduty.typetalk.data.entity

import androidx.room.*

@Entity(
    tableName = "account",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "login") var login: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "user_id") var userId: Long?,
    @TypeConverters(RoleConverter::class) var role: Role
)