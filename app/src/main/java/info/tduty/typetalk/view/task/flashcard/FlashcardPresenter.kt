package info.tduty.typetalk.view.task.flashcard

import info.tduty.typetalk.data.model.FlashcardVO
import info.tduty.typetalk.data.model.TaskType
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

    private val TASK_TYPE = TaskType.FLASHCARDS

    private val disposables = CompositeDisposable()
    private lateinit var flashcards: List<FlashcardVO>

    fun onCreate(lessonId: String) {
        disposables.add(
            taskInteractor.getTaskForLesson(lessonId.toLong(), TASK_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ taskVO ->
                    this.flashcards = taskVO as? List<FlashcardVO> ?: Collections.emptyList()
                    view.setupFlashcards(flashcards)
                }, Timber::e)
        )
    }

    fun onClickNext(currentPosition: Int) {
        if (currentPosition + 1 < flashcards.size) {
            view.showWord(currentPosition + 1, true)
        }
    }

    fun onDestroy() {

    }
}