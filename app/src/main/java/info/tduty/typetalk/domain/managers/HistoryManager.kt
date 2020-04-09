package info.tduty.typetalk.domain.managers

import info.tduty.typetalk.domain.interactor.ChatInteractor
import info.tduty.typetalk.domain.interactor.HistoryInteractor
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 06.04.2020.
 */
class HistoryManager(
    socketManager: SocketManager,
    private val chatInteractor: ChatInteractor,
    private val historyInteractor: HistoryInteractor
) {

    private var historyDisposable: Disposable? = null
    private var socketOnOpenDisposable: Disposable? = null

    init {
        socketOnOpenDisposable?.dispose()
        socketOnOpenDisposable = socketManager.onOpened()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ getHistory() }, Timber::e)
    }

    private fun getHistory() {
        historyDisposable?.dispose()
        historyDisposable = chatInteractor.loadAllChats()
            .doOnError { Timber.e(it) }
            .flatMapCompletable { historyInteractor.loadHistory() }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ }, Timber::e)
    }
}
