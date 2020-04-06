package info.tduty.typetalk.domain.provider

import info.tduty.typetalk.api.HistoryApi
import info.tduty.typetalk.data.dto.MessageDTO
import info.tduty.typetalk.data.pref.TokenStorage
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
class HistoryProvider(
    private val historyApi: HistoryApi,
    private val tokenStorage: TokenStorage
) {

    fun getHistory(): Observable<List<MessageDTO>> {
        return historyApi.getHistory(tokenStorage.getToken())
    }

    fun getHistory(chatId: String): Observable<List<MessageDTO>> {
        return historyApi.getHistory(tokenStorage.getToken(), chatId)
    }
}