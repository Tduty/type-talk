package info.tduty.typetalk.data.pref

import android.content.Context
import android.content.SharedPreferences


class SharedPreferencesHelper(context: Context, prefsName: String?, mode: Int) : PreferencesHelper {

    private var preferences: SharedPreferences = context.getSharedPreferences(prefsName, mode)

     override fun putString(key: String?, value: String?) {
        preferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String?): String? {
        return preferences.getString(key, null)
    }

    override fun putBoolean(key: String?, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String?): Boolean {
        return preferences.getBoolean(key, false)
    }

    override fun putInt(key: String?, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    override fun getInt(key: String?, defValue: Int): Int {
        return preferences.getInt(key, defValue)
    }

    override fun putLong(key: String?, value: Long) {
        preferences.edit().putLong(key, value).apply()
    }

    override fun getLong(key: String?, defValue: Long): Long {
        return preferences.getLong(key, defValue)
    }

    override fun clear() {
        preferences.edit().clear().apply()
    }
}
