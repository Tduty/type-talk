package info.tduty.typetalk.data.pref

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

internal class SharedPreferenceHelper(val context: Context, val gson: Gson) :
    PreferenceHelper {

    private val APP_PREFERENCES = "typetalk-data"
    private val APP_PREFERENCES_USER = "typetalk-data-user"
    private val FAILED_LOAD_MESSAGE = "Failed load"
    private var preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
    }

    override fun setUserData(userData: UserData) {
        val userJson = gson.toJson(userData)
        saveUserJsonData(userJson)

    }

    private fun saveUserJsonData(userJson: String) {
        val editor = preferences.edit()
        editor.putString(APP_PREFERENCES_USER, userJson)
        editor.apply()
    }

    override fun getSavedUser(): UserData? {
        if (preferences.contains(APP_PREFERENCES_USER)) {
            val data = preferences.getString(APP_PREFERENCES_USER, FAILED_LOAD_MESSAGE)
            data?.let { return getUserForJson(data) }
        }
        return null
    }

    override fun isSavedUser(): Boolean {
        return preferences.contains(APP_PREFERENCES_USER) && getUserForJson(preferences.getString(APP_PREFERENCES_USER, FAILED_LOAD_MESSAGE)) != null
    }

    private fun getUserForJson(userJson: String?) : UserData? {
        try {
            return gson.fromJson(userJson, UserData::class.java)
        } catch (ex: JsonSyntaxException) {
            ex.printStackTrace()
        }
        return null
    }
}
