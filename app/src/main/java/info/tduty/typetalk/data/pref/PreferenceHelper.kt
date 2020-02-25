package info.tduty.typetalk.data.pref

internal interface PreferenceHelper {
    fun setUserData(userData: UserData)
    fun getSavedUser() : UserData?
    fun isSavedUser() : Boolean
}
