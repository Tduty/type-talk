package info.tduty.typetalk.view.task.flashcard

import info.tduty.typetalk.R
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

    private val BTN_TITLE_NEXT = R.string.task_btn_next
    private val BTN_TITLE_COMPLETED = R.string.task_btn_complete

    fun onCreate(taskVO: TaskVO) {
        val flashcardsPayload = taskInteractor.getPayload2(taskVO)
        this.flashcards = getFlashcards(flashcardsPayload)

        if (this.flashcards.isEmpty()) {
            view.showError()
        }

        view.setupFlashcards(flashcards)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getFlashcards(listTaskPayload: List<TaskPayloadVO>): List<FlashcardVO> {
        return listTaskPayload as? List<FlashcardVO> ?: Collections.emptyList()
    }

    fun onClickNext(currentPosition: Int) {
        if (currentPosition == flashcards.size - 1) {
            view.completeTask()
        } else if (currentPosition + 1 < flashcards.size) {
            view.showWord(currentPosition + 1, true)
        }
    }

    fun onDestroy() {
        disposables.dispose()
    }

    fun onPageScrolled(position: Int) {
        if (position == flashcards.size - 1) {
            view.setTitleNextButton(BTN_TITLE_COMPLETED)
        } else {
            view.setTitleNextButton(BTN_TITLE_NEXT)
        }
    }
}