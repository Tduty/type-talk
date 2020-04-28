package info.tduty.typetalk.view.teacher.manage.lessons

import info.tduty.typetalk.data.model.LessonManageVO

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
interface LessonsManageView {

    fun setLessons(lessons: List<LessonManageVO>)

    fun openManageTasks(classId: String, lesson: LessonManageVO)
}
