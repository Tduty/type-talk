package info.tduty.typetalk.api

import info.tduty.typetalk.data.dto.UserDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Created by Evgeniy Mezentsev on 04.04.2020.
 */
interface LoginApi {

    companion object {
        const val ENDPOINT_PREFIX = "/principal/"
    }

    @GET("auth")
    fun auth(@Header("Authorization") token: String): Observable<UserDTO>
}