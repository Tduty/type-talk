package info.tduty.typetalk.api

import info.tduty.typetalk.data.dto.StudentDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * Created by Evgeniy Mezentsev on 19.04.2020.
 */
interface StudentApi {

    companion object {
        const val ENDPOINT_PREFIX = "/student/"
    }

    @GET("/student/{class_id}")
    fun getStudents(@Header("Authorization") token: String,
                    @Path("class_id") classId: String): Observable<List<StudentDTO>>
}