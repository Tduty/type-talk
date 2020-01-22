package info.tduty.typetalk.data.entity

import androidx.room.TypeConverter

class RoleConverter {

    @TypeConverter
    fun toRole(role: Int): Role {
        return when (role) {
            Role.ADMIN.code -> Role.ADMIN
            Role.TEACHER.code -> Role.TEACHER
            Role.STUDENT.code -> Role.STUDENT
            else -> Role.ADMIN
        }
    }

    @TypeConverter
    fun toInteger(role: Role): Int {
        return role.code
    }
}