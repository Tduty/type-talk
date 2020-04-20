package info.tduty.typetalk.view.teacher.classinfo

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.StudentVO

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
class RvStudentAdapter(private val listener: (String) -> Unit) : RecyclerView.Adapter<StudentVH>() {

    private val students: MutableList<StudentVO> = ArrayList()

    fun setStudents(students: List<StudentVO>) {
        this.students.clear()
        this.students.addAll(students)
        notifyDataSetChanged()
    }

    fun addStudent(classVO: StudentVO) {
        students.add(classVO)
        notifyItemRangeChanged(students.size - 1, 1)
    }

    fun updateStatus(studentId: String, status: StudentVO.Status) {
        val index = students.indexOfFirst { it.id == studentId }
        if (index == -1) return
        students.getOrNull(index)?.status = status
        notifyItemChanged(index)
    }

    fun updateAction(studentId: String, action: String) {
        val index = students.indexOfFirst { it.id == studentId }
        if (index == -1) return
        students.getOrNull(index)?.action = action
        notifyItemChanged(index)
    }

    fun updateExistNewMessages(chatId: String, exist: Boolean) {
        val index = students.indexOfFirst { it.chatId == chatId }
        if (index == -1) return
        students.getOrNull(index)?.existNewMessages = exist
        notifyItemChanged(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentVH.newInstance(parent, listener)

    override fun getItemCount() = students.size

    override fun onBindViewHolder(holder: StudentVH, position: Int) = holder.onBind(students[position])
}
