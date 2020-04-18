package info.tduty.typetalk.domain.interactor

import android.util.Base64
import com.google.gson.JsonObject
import info.tduty.typetalk.data.dto.UserDTO
import info.tduty.typetalk.data.pref.TokenStorage
import info.tduty.typetalk.data.pref.UserData
import info.tduty.typetalk.data.pref.UserDataHelper
import info.tduty.typetalk.domain.provider.LoginProvider
import io.reactivex.Completable
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 04.04.2020.
 */
class LoginInteractor(private val userDataHelper: UserDataHelper,
                      private val tokenStorage: TokenStorage,
                      private val loginProvider: LoginProvider) {

    companion object {
        private const val LOGIN_CODE = "login"
        private const val PASSWORD_CODE = "password"
    }

    fun auth(login: String, password: String): Completable {
        val base64 = Base64.encodeToString(("$login:$password").toByteArray(), Base64.NO_WRAP)
        val token = "Basic $base64"
        return loginProvider.auth(token)
            .doOnError { Timber.e(it) }
            .flatMapCompletable {
                Timber.d("[LOGIN] Successful")
                userDataHelper.setUserData(mapToUserData(it))
                tokenStorage.setToken(token)
                Completable.complete()
            }
    }

    fun auth(dataJson: String): Completable {
        var login = ""
        var password = ""
        try {
            val json = JSONObject(dataJson)

            login = json.getString(LOGIN_CODE)
            password = json.getString(PASSWORD_CODE)

        } catch (ex: JSONException) {
            ex.printStackTrace()
        } finally {
            return auth(login, password)
        }
    }

    private fun mapToUserData(userDTO: UserDTO): UserData {
        return UserData(
            id = userDTO.id,
            surname = userDTO.name,
            classNumber = userDTO.className ?: "null",
            isTeacher = userDTO.isTeacher
        )
    }
}
