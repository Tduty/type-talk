package info.tduty.typetalk.view.task.flashcard

import info.tduty.typetalk.data.model.FlashcardVO
import info.tduty.typetalk.data.model.TaskPayloadVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.domain.interactor.TaskInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*

class FlashcardPresenter(
    val view: FlashcardView,
    private val taskInteractor: TaskInteractor
) {

    private val disposables = CompositeDisposable()
    private lateinit var flashcards: List<FlashcardVO>

    fun onCreate(taskVO: TaskVO) {
        disposables.add(
            taskInteractor.getPayload(taskVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ task ->
                    this.flashcards = getFlashcards(task)

                    if (this.flashcards.isEmpty()) {
                        view.showError()
                    }

                    view.setupFlashcards(flashcards)
                }, Timber::e)
        )
    }

    @Suppress("UNCHECKED_CAST")
    private fun getFlashcards(listTaskPayload: List<TaskPayloadVO>): List<FlashcardVO> {
        return listTaskPayload as? List<FlashcardVO> ?: Collections.emptyList()
    }

    fun onClickNext(currentPosition: Int) {
        if (currentPosition + 1 < flashcards.size) {
            view.showWord(currentPosition + 1, true)
        }
    }

    fun onDestroy() {
        disposables.dispose()
    }
}