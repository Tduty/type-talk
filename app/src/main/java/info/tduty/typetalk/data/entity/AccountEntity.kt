package info.tduty.typetalk.data.entity

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "account")
@TypeConverters(RoleConverter::class)
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "login") var login: String?,
    @ColumnInfo(name = "password") var password: String?,
    @Embedded var user: UserEntity?,
    var role: Role
)