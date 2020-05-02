package info.tduty.typetalk.view.task.hurryup

import info.tduty.typetalk.data.model.HurryUpVO

interface HurryUpView {

    fun completeTask()

    fun setupHurryUp(hurryUpList: List<HurryUpVO>)

    fun showError()

    fun nextPage(isAnimated: Boolean)

    fun moveToPage(position: Int, isAnimated: Boolean)

    fun showCompleteAlertDialog(title: Int, message: Int, countCompletedTask: Int, isTryAgain: Boolean)

    fun showStartAlertDialog(title: Int, message: Int)

    fun setupTimer(seconds: Long)

    fun startTimer()

    fun stopTimer()

    fun disableUI(isDisable: Boolean)

    fun setPenatlyForTimer(second: Int)
}