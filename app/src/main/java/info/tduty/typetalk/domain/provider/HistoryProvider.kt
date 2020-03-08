package info.tduty.typetalk.domain.provider

import info.tduty.typetalk.api.HistoryApi
import info.tduty.typetalk.data.dto.MessageDTO
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
class HistoryProvider(private val historyApi: HistoryApi) {

    fun getHistory(chatId: String): Observable<List<MessageDTO>> {
        return historyApi.getHistory(chatId)
    }
}