package info.tduty.typetalk.api

import info.tduty.typetalk.data.dto.ClassDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
interface ClassApi {

    companion object {
        const val ENDPOINT_PREFIX = "/class/"
    }

    @GET(".")
    fun getClasses(@Header("Authorization") token: String): Observable<List<ClassDTO>>
}