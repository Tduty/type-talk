package info.tduty.typetalk.view.lesson

import info.tduty.typetalk.data.model.TaskVO

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */
interface LessonView {

    fun setTasks(tasks: List<TaskVO>)
}