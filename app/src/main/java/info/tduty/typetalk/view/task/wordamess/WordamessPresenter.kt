package info.tduty.typetalk.view.task.wordamess

import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.data.model.WordamessVO
import info.tduty.typetalk.domain.interactor.TaskInteractor

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
class WordamessPresenter(
    private val view: WordamessView,
    private val taskInteractor: TaskInteractor
) {

    private lateinit var tasks: Map<String, WordamessVO>
    private val selectedTasks = hashMapOf<String, WordamessVO>()

    fun onCreate(taskVO: TaskVO) {
        val tasks = taskInteractor.getPayload2(taskVO) as? List<WordamessVO> ?: emptyList()
        this.tasks = tasks.map { it.body to it }.toMap()
        view.setupTasks(tasks as? List<WordamessVO> ?: emptyList())
    }

    fun selectWord(body: String) {
        if (selectedTasks.contains(body)) return
        view.setClickableBtn(true)
        selectedTasks[body] = tasks[body] ?: return
        view.setCorrectWordCount(selectedTasks.size)
    }

    fun deselectWord(body: String) {
        if (!selectedTasks.contains(body)) return
        selectedTasks.remove(body)
        if (selectedTasks.isEmpty()) view.setClickableBtn(false)
        view.setCorrectWordCount(selectedTasks.size)
    }

    fun onCorrect() {
        if (selectedTasks.all { it.value.isMistake }) {

        } else view.showErrorTasksEmpty()
    }

    fun onDestroy() {
    }
}