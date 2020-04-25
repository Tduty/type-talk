package info.tduty.typetalk.data.pref

import android.content.Context

class TokenSharedPreferencesHelper(context: Context) : TokenStorage {

    private val TOKEN = "token"
    private var preferences = SharedPreferencesHelper(context, Context.MODE_PRIVATE)

    override fun setToken(token: String) {
        preferences.putString(TOKEN, token)
    }

    override fun getToken(): String {
        return preferences.getString(TOKEN) ?: throw IllegalArgumentException("token is null")
    }
}
