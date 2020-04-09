package info.tduty.typetalk.data.pref

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

class UserDataSharedPreferencesHelper(
    val context: Context,
    val gson: Gson
) : UserDataHelper {

    private val APP_PREFERENCES_USER = "typetalk-data-user"
    private var preferences: SharedPreferencesHelper = SharedPreferencesHelper(context, MODE_PRIVATE)

    override fun setUserData(userData: UserData) {
        val userJson = gson.toJson(userData)
        saveUserJsonData(userJson)
    }

    private fun saveUserJsonData(userJson: String) {
        preferences.putString(APP_PREFERENCES_USER, userJson)
    }

    override fun getSavedUser(): UserData {
        val data = preferences.getString(APP_PREFERENCES_USER)
        return data?.let { getUserForJson(data) } ?: throw IllegalArgumentException("unknown user")
    }

    override fun isSavedUser(): Boolean {
        return getUserForJson(preferences.getString(APP_PREFERENCES_USER)) != null
    }

    private fun getUserForJson(userJson: String?): UserData? {
        return try {
            gson.fromJson(userJson, UserData::class.java)
        } catch (ex: JsonSyntaxException) {
            ex.printStackTrace()
            null
        }
    }
}
