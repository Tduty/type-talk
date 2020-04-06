package info.tduty.typetalk.data.pref

/**
 * Created by Evgeniy Mezentsev on 05.04.2020.
 */
interface TokenStorage {
    fun setToken(token: String)
    fun getToken() : String
}