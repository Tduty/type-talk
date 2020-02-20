package info.tduty.typetalk.view.lesson

import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.view.dictionary.DictionaryView
import javax.inject.Inject

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */
class LessonPresenter {

    private lateinit var view: LessonView

    fun onAttach(view: LessonView) {
        this.view = view
    }

    fun onCreate() {

    }

    fun onDestroy() {

    }

    fun openTask(id: String, task: TaskType) {

    }
}
