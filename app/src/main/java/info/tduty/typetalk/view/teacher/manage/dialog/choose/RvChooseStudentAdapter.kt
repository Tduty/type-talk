package info.tduty.typetalk.view.teacher.manage.dialog.choose

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.ChooseStudentVO

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class RvChooseStudentAdapter(private val listener: (String) -> Unit) : RecyclerView.Adapter<ChooseStudentVH>() {

    private val students: MutableList<ChooseStudentVO> = ArrayList()

    fun setStudents(students: List<ChooseStudentVO>) {
        this.students.clear()
        this.students.addAll(students)
        notifyDataSetChanged()
    }

    fun addStudent(student: ChooseStudentVO) {
        students.add(student)
        notifyItemRangeChanged(students.size - 1, 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChooseStudentVH.newInstance(parent, listener)

    override fun getItemCount() = students.size

    override fun onBindViewHolder(holder: ChooseStudentVH, position: Int) = holder.onBind(students[position])
}
