package info.tduty.typetalk.data.pref

interface UserDataHelper {
    fun removeUserData()
    fun setUserData(userData: UserData)
    fun getSavedUser() : UserData
    fun isSavedUser() : Boolean
}
