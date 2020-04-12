package info.tduty.typetalk.view.dictionary

import info.tduty.typetalk.domain.interactor.DictionaryInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class DictionaryPresenter(private val view: DictionaryView,
                          private val dictionaryInteractor: DictionaryInteractor) {

    private val disposable = CompositeDisposable()

    fun onCreate() {
        getDictionary()
    }

    fun onDestroy() {
        disposable.dispose()
    }

    private fun getDictionary() {
        disposable.add(
            dictionaryInteractor.getDictionary()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dictionaries ->
                    if (dictionaries.isEmpty()) view.showEmptyPlaceholder()
                    else view.hideEmptyPlaceholder()
                    view.setDictionary(dictionaries)
                }, Timber::e)
        )
    }
}
