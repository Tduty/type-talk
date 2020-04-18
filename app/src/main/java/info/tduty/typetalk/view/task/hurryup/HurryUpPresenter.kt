package info.tduty.typetalk.view.task.hurryup

import android.os.Handler
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.HurryUpVO
import info.tduty.typetalk.data.model.TaskPayloadVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.domain.interactor.TaskInteractor
import java.util.*


class HurryUpPresenter(
    val view: HurryUpView,
    val taskInteractor: TaskInteractor
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

        view.setupHurryUp(hurryUpList)
    }

    private fun getHurryUpList(payload2: List<TaskPayloadVO>): List<HurryUpVO> {
        return payload2 as? List<HurryUpVO> ?: Collections.emptyList()
    }

    fun onClickListener(hurryUpVO: HurryUpVO) {
        hurryUpVO.isComplete = true

        val handler = Handler()
        val runnable = Runnable {
            view.nextPage(true)
        }

        handler.postDelayed(runnable,400)
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
            !isCompleted
            )
    }

    private fun isCompleted() : Boolean {
        return hurryUpList.find { !it.isComplete } == null
    }

    fun startTask() {
        view.startTimer()
    }

    fun onPause() {
        view.stopTimer()
    }
}