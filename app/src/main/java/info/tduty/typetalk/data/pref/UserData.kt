package info.tduty.typetalk.data.pref

data class UserData(
    val indexDB: Long = 0,
    val id: String,
    val surname: String,
    val classNumber: String,
    val isTeacher: Boolean
)
