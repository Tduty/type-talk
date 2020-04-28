package info.tduty.typetalk.view.teacher.manage.dialog.choose

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.ChooseStudentVO
import info.tduty.typetalk.data.model.StudentVO
import kotlinx.android.synthetic.main.item_choose_student.view.*

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class ChooseStudentVH(
    itemView: View,
    private val listener: (String) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun newInstance(parent: ViewGroup, listener: (String) -> Unit): ChooseStudentVH {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_choose_student, parent, false)
            return ChooseStudentVH(view, listener)
        }
    }

    fun onBind(student: ChooseStudentVO) {
        itemView.iv_student_icon.setImageResource(student.type.icon)
        itemView.tv_student_name.text = student.name
        setupStatus(student.status)
        setupChecked(student.isChecked)
        itemView.setOnClickListener {
            student.isChecked = !student.isChecked
            setupChecked(student.isChecked)
            listener.invoke(student.id)
        }
    }

    private fun setupStatus(status: StudentVO.Status) {
        val color = when (status) {
            StudentVO.Status.NOT_CONNECTED -> R.color.guardsman_red
            StudentVO.Status.DISCONNECTED -> R.color.silver
            StudentVO.Status.CONNECTED -> R.color.jade
        }
        itemView.iv_student_status.setColorFilter(getColor(color))
    }

    private fun setupChecked(isChecked: Boolean) {
        if (isChecked) itemView.iv_checked.setImageResource(R.drawable.ic_checkbox_complete_dark)
        else itemView.iv_checked.setImageResource(R.drawable.ic_checkbox_empty_dark)
    }

    private fun getColor(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(itemView.context, resId)
    }
}
