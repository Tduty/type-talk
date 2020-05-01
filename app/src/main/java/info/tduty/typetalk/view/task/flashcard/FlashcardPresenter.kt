package info.tduty.typetalk.view.task.flashcard

import info.tduty.typetalk.R
import info.tduty.typetalk.data.event.payload.CompleteTaskPayload
import info.tduty.typetalk.data.model.FlashcardVO
import info.tduty.typetalk.data.model.TaskPayloadVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.domain.interactor.TaskInteractor
import info.tduty.typetalk.socket.SocketController
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import kotlin.math.abs

class FlashcardPresenter(
    val view: FlashcardView,
    private val taskInteractor: TaskInteractor,
    private val socketController: SocketController
) {

    private var task: TaskVO? = null
    private val disposables = CompositeDisposable()

    private lateinit var flashcards: List<FlashcardVO>
    private var visibleWors: MutableList<FlashcardVO> = Collections.emptyList()

    private val BTN_TITLE_NEXT = R.string.task_btn_next
    private val BTN_TITLE_COMPLETED = R.string.task_btn_complete

    private val COUNT_WORDS_FOR_TASK = 25

    fun onCreate(taskVO: TaskVO) {
        this.task = taskVO
        val flashcardsPayload = taskInteractor.getPayload2(taskVO)
        this.flashcards = getFlashcards(flashcardsPayload)

        if (this.flashcards.isEmpty()) {
            view.showError()
        }
        visibleWors = flashcards.shuffled().subList(0, COUNT_WORDS_FOR_TASK).toMutableList()
        view.setupFlashcards(visibleWors)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getFlashcards(listTaskPayload: List<TaskPayloadVO>): List<FlashcardVO> {
        return listTaskPayload as? List<FlashcardVO> ?: Collections.emptyList()
    }

    fun onClickNext(currentPosition: Int) {
        if (currentPosition == visibleWors.size - 1) {
            if (visibleWors.size < flashcards.size) {
                view.completedWithNext()
            } else if (visibleWors.size == flashcards.size) {
                sendEventCompleteTask()
                view.completeTask()
            }
        } else if (currentPosition + 1 < visibleWors.size) {
            view.showWord(currentPosition + 1, true)
        }
    }

    fun onDestroy() {
        disposables.dispose()
    }

    fun onPageScrolled(position: Int) {
        if (position == visibleWors.size - 1) {
            if (visibleWors.size < flashcards.size) {
                view.setTitleNextButton(BTN_TITLE_NEXT)
            } else if (visibleWors.size == flashcards.size) {
                view.setTitleNextButton(BTN_TITLE_COMPLETED)
            }
        } else {
            view.setTitleNextButton(BTN_TITLE_NEXT)
        }
    }

    fun sendEventCompleteTask() {
        socketController.sendCompleteTask(
            CompleteTaskPayload(
                task?.lessonId ?: "",
                task?.id ?: "",
                true
            )
        )
    }

    fun nextExecuteTask() {
        val countVisibleWords = visibleWors.size
        val counrRemainingWords = flashcards.size - visibleWors.size

        if (abs(COUNT_WORDS_FOR_TASK - counrRemainingWords) < 10) {
            visibleWors.addAll(flashcards.subList(countVisibleWords - 1, flashcards.size))
        } else {
            visibleWors.addAll(flashcards.subList(countVisibleWords - 1, countVisibleWords - 1 + COUNT_WORDS_FOR_TASK))
        }

        view.setupFlashcards(visibleWors)
    }
}