package info.tduty.typetalk.view.teacher.classinfo

import info.tduty.typetalk.data.model.StudentVO

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
interface ClassView {

    fun setToolbar(title: String)

    fun setStudents(students: List<StudentVO>)

    fun updateStatus(studentId: String, status: StudentVO.Status)

    fun updateAction(studentId: String, action: String)

    fun updateExistNewMessages(chatId: String, exist: Boolean)

    fun openChat(chatId: String)

    fun openClassChat()
}
