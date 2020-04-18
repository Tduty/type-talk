package info.tduty.typetalk.domain.provider

import info.tduty.typetalk.api.StudentApi
import info.tduty.typetalk.data.dto.StudentDTO
import info.tduty.typetalk.data.pref.TokenStorage
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 19.04.2020.
 */
class StudentProvider(
    private val studentApi: StudentApi,
    private val tokenStorage: TokenStorage
) {

    fun getStudents(classId: String): Observable<List<StudentDTO>> {
        return studentApi.getStudents(tokenStorage.getToken(), classId)
    }
}
