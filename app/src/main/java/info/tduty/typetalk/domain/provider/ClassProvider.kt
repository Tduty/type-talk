package info.tduty.typetalk.domain.provider

import info.tduty.typetalk.api.ClassApi
import info.tduty.typetalk.data.dto.ClassDTO
import info.tduty.typetalk.data.pref.TokenStorage
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
class ClassProvider(
    private val classApi: ClassApi,
    private val tokenStorage: TokenStorage
) {

    fun getClasses(): Observable<List<ClassDTO>> {
        return classApi.getClasses(tokenStorage.getToken())
    }
}