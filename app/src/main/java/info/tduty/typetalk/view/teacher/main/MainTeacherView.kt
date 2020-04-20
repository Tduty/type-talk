package info.tduty.typetalk.view.teacher.main

import info.tduty.typetalk.data.model.ClassVO

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
interface MainTeacherView {

    fun setClasses(classes: List<ClassVO>)

    fun openClassScreen(classId: String, className: String)
}