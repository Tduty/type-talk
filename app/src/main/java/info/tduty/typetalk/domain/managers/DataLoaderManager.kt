package info.tduty.typetalk.domain.managers

import info.tduty.typetalk.domain.interactor.ChatInteractor
import info.tduty.typetalk.domain.interactor.DictionaryInteractor
import info.tduty.typetalk.domain.interactor.HistoryInteractor
import info.tduty.typetalk.domain.interactor.LessonInteractor
import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 06.04.2020.
 */
class DataLoaderManager(
    socketManager: SocketManager,
    private val databaseManager: DatabaseManager,
    private val chatInteractor: ChatInteractor,
    private val historyInteractor: HistoryInteractor,
    private val lessonInteractor: LessonInteractor,
    private val dictionaryInteractor: DictionaryInteractor
) {

    private var disposable: Disposable? = null
    private var socketOnOpenDisposable: Disposable? = null

    init {
        socketOnOpenDisposable?.dispose()
        socketOnOpenDisposable = socketManager.onOpened()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ loadDataInBackground() }, Timber::e)
    }

    fun loadData(): Completable {
        return Completable.concatArray(
            getHistory(),
            getLessons(),
            getDictionary()
        ).onErrorComplete()
    }

    private fun loadDataInBackground() {
        disposable?.dispose()
        disposable = loadData()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ databaseManager.postLessonUpdated() }, Timber::e)
    }

    private fun getHistory(): Completable {
        return chatInteractor.loadAllChats()
            .doOnError { Timber.e(it) }
            .flatMapCompletable { historyInteractor.loadHistory() }
    }

    private fun getLessons(): Completable {
        return lessonInteractor.loadLessons()
            .doOnError { Timber.e(it) }
            .doOnComplete { databaseManager.postLessonUpdated() }
    }

    private fun getDictionary(): Completable {
        return dictionaryInteractor.loadDictionary()
            .doOnError { Timber.e(it) }
    }
}
