package info.tduty.typetalk.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "account")
data class AccountEntity(
    var id: Long,
    @ColumnInfo(name = "login") var login: Long,
    @ColumnInfo(name = "password") var password: Long,
    @ColumnInfo(name = "user") var userId: Long
)