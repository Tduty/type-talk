package info.tduty.typetalk.view.task.phrasebuilding

import info.tduty.typetalk.data.model.PhraseBuildingVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.domain.interactor.TaskInteractor

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
class PhraseBuildingPresenter(
    val view: PhraseBuildingView,
    private val taskInteractor: TaskInteractor
) {

    private lateinit var lessonId: String
    private lateinit var tasks: Map<String, PhraseBuildingVO>

    fun onCreate(taskVO: TaskVO) {
        this.lessonId = taskVO.lessonId
        val tasks = taskInteractor.getPayload2(taskVO) as? List<PhraseBuildingVO> ?: emptyList()
        this.tasks = tasks.map { it.id to it }.toMap()
        view.setupPhrases(tasks)
    }

    fun nextPage(id: String) {
        view.nextPage()
    }


    fun completeTask() {
        view.openLesson(lessonId)
    }

    fun onDestroy() {

    }
}
