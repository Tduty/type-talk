package info.tduty.typetalk.data.model

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
data class ChooseStudentVO(
    val id: String,
    val name: String,
    val type: StudentVO.Type,
    var status: StudentVO.Status,
    var isChecked: Boolean = false
) {

    companion object {

        fun map(student: StudentVO): ChooseStudentVO {
            return ChooseStudentVO(
                id = student.id,
                name = student.name,
                type = student.type,
                status = student.status
            )
        }
    }
}
