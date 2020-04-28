package info.tduty.typetalk.view.task.hurryup

import android.os.Handler
import info.tduty.typetalk.R
import info.tduty.typetalk.data.event.payload.CompleteTaskPayload
import info.tduty.typetalk.data.model.HurryUpVO
import info.tduty.typetalk.data.model.TaskPayloadVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.domain.interactor.TaskInteractor
import info.tduty.typetalk.socket.SocketController
import info.tduty.typetalk.utils.Utils
import java.util.*


class HurryUpPresenter(
    val view: HurryUpView,
    val taskInteractor: TaskInteractor,
    val socketController: SocketController
) {

    private var task: TaskVO? = null
    private var hurryUpList: List<HurryUpVO> = emptyList()

    private val titleStartAlertDialog = R.string.task_screen_hurry_up_title_start_alert_dialog
    private val messageStartAlertDialog = R.string.task_screen_hurry_up_message_start_alert_dialog

    private val titleCompletedAlertDialog = R.string.task_screen_hurry_up_title_completed_alert_dialog
    private val messagePositiveCompletedAlertDialog = R.string.task_screen_hurry_up_message_positive_completed_alert_dialog
    private val messageNegativeCompletedAlertDialog = R.string.task_screen_hurry_up_message_negative_completed_alert_dialog

    private var isStart = false

    fun onCreate(taskVO: TaskVO) {
        this.hurryUpList = getHurryUpList(taskInteractor.getPayload2(taskVO))

        if (this.hurryUpList.isEmpty()) {
            view.showError()
        }

        this.task = taskVO

        view.setupHurryUp(hurryUpList.shuffled())
    }

    private fun getHurryUpList(payload2: List<TaskPayloadVO>): List<HurryUpVO> {
        return payload2 as? List<HurryUpVO> ?: Collections.emptyList()
    }

    fun onSelectWord(selectedWord: String, hurryUpVO: HurryUpVO) {
        if (hurryUpVO.translate == selectedWord) {
            hurryUpVO.isComplete = true

            val handler = Handler()
            val runnable = Runnable {
                view.nextPage(true)
            }

            handler.postDelayed(runnable, 500)
        } else {
            view.setPenatlyForTimer(5)
        }
    }

    fun onStart() {
        if (!isStart) {
            isStart = true
            view.showStartAlertDialog(titleStartAlertDialog, messageStartAlertDialog)
        } else {
            view.startTimer()
        }
    }

    fun tryAgain() {
        view.startTimer()
    }

    fun completedTask() {
        val isCompleted = isCompleted()
        view.stopTimer()
        view.showCompleteAlertDialog(
            titleCompletedAlertDialog,
            if (isCompleted) messagePositiveCompletedAlertDialog else messageNegativeCompletedAlertDialog,
            hurryUpList.filter { it.isComplete }.size,
            !isCompleted
        )
    }

    private fun isCompleted() : Boolean {
        val countSuccessTask = hurryUpList.filter { it.isComplete }.size
        val countTask = hurryUpList.size
        return Utils.getSuccessCompletedTaskPercent(countTask, countSuccessTask) >= 50
    }

    fun startTask() {
        view.startTimer()
    }

    fun onPause() {
        view.stopTimer()
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
}