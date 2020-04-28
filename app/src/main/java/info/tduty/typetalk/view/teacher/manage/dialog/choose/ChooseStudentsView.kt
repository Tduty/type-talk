package info.tduty.typetalk.view.teacher.manage.dialog.choose

import info.tduty.typetalk.data.model.ChooseStudentVO

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
interface ChooseStudentsView {

    fun setStudents(students: List<ChooseStudentVO>)

    fun closeView()
}
