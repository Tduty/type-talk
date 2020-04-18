package info.tduty.typetalk.view.teacher.classinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.StudentVO
import kotlinx.android.synthetic.main.item_student.view.*

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
class StudentVH(
    itemView: View,
    private val listener: (String) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun newInstance(parent: ViewGroup, listener: (String) -> Unit): StudentVH {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_student, parent, false)
            return StudentVH(view, listener)
        }
    }

    fun onBind(student: StudentVO) {
        itemView.iv_student_icon.setImageResource(student.type.icon)
        itemView.tv_student_name.text = student.name
        setupStatus(student.status)
        setupAction(student)
        setupNewMessages(student.existNewMessages)
        itemView.setOnClickListener { listener.invoke(student.chatId) }
    }

    private fun setupStatus(status: StudentVO.Status) {
        val color = when (status) {
            StudentVO.Status.NOT_CONNECTED -> R.color.guardsman_red
            StudentVO.Status.DISCONNECTED -> R.color.silver
            StudentVO.Status.CONNECTED -> R.color.jade
        }
        itemView.iv_student_status.setColorFilter(getColor(color))
    }

    private fun setupAction(student: StudentVO) {
        val action = when (student.status) {
            StudentVO.Status.NOT_CONNECTED -> getString(R.string.class_student_not_connected)
            StudentVO.Status.DISCONNECTED -> getString(R.string.class_student_disconnected)
            StudentVO.Status.CONNECTED -> student.action ?: ""
        }
        itemView.tv_student_action.text = action
    }

    private fun setupNewMessages(existNewMessages: Boolean) {
        itemView.tv_exist_new_messages.visibility = if (existNewMessages) View.VISIBLE
        else View.INVISIBLE
    }

    private fun getString(@StringRes resId: Int): String {
        return itemView.context.getString(resId)
    }

    private fun getColor(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(itemView.context, resId)
    }
}