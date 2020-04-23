package info.tduty.typetalk.view.teacher.manage.tasks

import androidx.annotation.DrawableRes
import info.tduty.typetalk.data.model.DialogVO
import info.tduty.typetalk.data.model.TaskManageVO

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
interface TasksManageView {

    fun setupLessonIcon(@DrawableRes iconRes: Int)

    fun setupLessonTitle(title: String)

    fun setupLessonCompleted(studentCount: Int, completedCount: Int)

    fun setTasks(tasks: List<TaskManageVO>)

    fun openDialogs(dialogs: List<DialogVO>)

    fun openChooseStudentsForDialogs(classId: String, lessonId: String, taskId: String)

    fun showSearchView()

    fun hideSearchView()
}
