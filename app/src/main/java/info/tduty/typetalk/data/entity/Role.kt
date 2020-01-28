package info.tduty.typetalk.data.entity

import androidx.room.TypeConverter

enum class Role(var code: Int) {
    ADMIN(0),
    TEACHER(1),
    STUDENT(2);
}