package info.tduty.typetalk.view.task.phrasebuilding

import info.tduty.typetalk.data.event.payload.CompleteTaskPayload
import info.tduty.typetalk.data.model.PhraseBuildingVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.domain.interactor.TaskInteractor
import info.tduty.typetalk.socket.SocketController
import info.tduty.typetalk.utils.Utils

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
class PhraseBuildingPresenter(
    val view: PhraseBuildingView,
    private val taskInteractor: TaskInteractor,
    val socketController: SocketController
) {

    private var taskVO: TaskVO? = null
    private lateinit var lessonId: String
    private lateinit var tasks: Map<String, PhraseBuildingVO>

    fun onCreate(taskVO: TaskVO) {
        this.taskVO = taskVO
        this.lessonId = taskVO.lessonId
        val tasks = taskInteractor.getPayload2(taskVO) as? List<PhraseBuildingVO> ?: emptyList()
        this.tasks = tasks.map { it.id to it }.toMap()
        view.setupPhrases(tasks)
    }

    fun nextPage(id: String) {
        view.nextPage()
    }

    fun completeTask() {
        val incorrect = tasks.values.filter { !it.isCorrectText }
        val countSuccessTask = tasks.values.size - incorrect.size
        val countTask = tasks.values.size
        val successCompletedTaskPercent =
            Utils.getSuccessCompletedTaskPercent(countTask, countSuccessTask)

        if (successCompletedTaskPercent >= 50) {
            successfulExecution(incorrect)
        } else {
            unsuccessfulExecution(incorrect)
        }
    }

    private fun unsuccessfulExecution(incorrect: List<PhraseBuildingVO>) {
        view.unsuccessComplete(incorrect)
    }

    private fun successfulExecution(incorrect: List<PhraseBuildingVO>) {
        if (incorrect.isNotEmpty()) {
            view.successCompletedWithIncorrectWord(incorrect)
        } else {
            sendEventCompleteTask()
            view.completeTask()
        }
    }

    fun onDestroy() {

    }

    fun tryAgain() {
        taskVO?.let {
            view.nextPage(0)
            onCreate(it)
        }
    }

    fun setInputText(
        id: String,
        buildWords: MutableList<String>?
    ) {
        tasks[id]?.buildingText =
            buildWords?.joinToString(prefix = "", postfix = "", separator = " ") ?: ""
    }

    fun setCorrectText(id: String, buildWords: MutableList<String>?) {
        tasks[id]?.buildingText =
            buildWords?.joinToString(prefix = "", postfix = "", separator = " ") ?: ""
        tasks[id]?.isCorrectText = true
    }

    fun sendEventCompleteTask() {
        socketController.sendCompleteTask(
            CompleteTaskPayload(
                taskVO?.lessonId ?: "",
                taskVO?.id ?: "",
                true
            )
        )
    }
}
