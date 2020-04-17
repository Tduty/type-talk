package info.tduty.typetalk.view.task.hurryup

import info.tduty.typetalk.data.model.HurryUpVO
import info.tduty.typetalk.data.model.TaskPayloadVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.domain.interactor.TaskInteractor
import java.util.*
import kotlin.concurrent.schedule

class HurryUpPresenter(
    val view: HurryUpView,
    val taskInteractor: TaskInteractor
) {

    private var task: TaskVO? = null
    private var hurryUpList: List<HurryUpVO> = emptyList()

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
        Timer("HurryUpCallback", true).schedule(300) {
            view.nextPage(true)
        }
    }
}