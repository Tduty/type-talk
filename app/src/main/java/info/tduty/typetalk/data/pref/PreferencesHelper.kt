package info.tduty.typetalk.data.pref

interface PreferencesHelper {

    fun putString(key: String?, value: String?)
    fun getString(key: String?): String?
    fun putBoolean(key: String?, value: Boolean)
    fun getBoolean(key: String?): Boolean
    fun putInt(key: String?, value: Int)
    fun getInt(key: String?, defValue: Int): Int
    fun putLong(key: String?, value: Long)
    fun getLong(key: String?, defValue: Long): Long
    fun clear()
}
