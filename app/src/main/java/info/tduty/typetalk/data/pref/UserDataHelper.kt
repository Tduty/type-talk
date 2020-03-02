package info.tduty.typetalk.data.pref

internal interface UserDataHelper {
    fun setUserData(userData: UserData)
    fun getSavedUser() : UserData?
    fun isSavedUser() : Boolean
}
