package info.tduty.typetalk.domain.provider

import info.tduty.typetalk.api.LoginApi
import info.tduty.typetalk.data.dto.UserDTO
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 04.04.2020.
 */
class LoginProvider(private val loginApi: LoginApi) {

    fun auth(token: String): Observable<UserDTO> {
        return loginApi.auth(token)
    }
}