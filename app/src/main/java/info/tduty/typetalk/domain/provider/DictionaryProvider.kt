package info.tduty.typetalk.domain.provider

import info.tduty.typetalk.api.DictionaryApi
import info.tduty.typetalk.data.dto.DictionaryDTO
import info.tduty.typetalk.data.pref.TokenStorage
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 09.04.2020.
 */
class DictionaryProvider(
    private val dictionaryApi: DictionaryApi,
    private val tokenStorage: TokenStorage
) {

    fun getDictionary(): Observable<List<DictionaryDTO>> {
        return dictionaryApi.getDictionary(tokenStorage.getToken(), 1)
    }

    fun getDictionary(lessonId: String): Observable<List<DictionaryDTO>> {
        return dictionaryApi.getDictionary(tokenStorage.getToken(), lessonId)
    }
}