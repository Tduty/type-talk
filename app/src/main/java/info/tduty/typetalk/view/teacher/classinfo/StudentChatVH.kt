package info.tduty.typetalk.view.teacher.classinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.ClassVO
import info.tduty.typetalk.view.teacher.main.ClassVH
import kotlinx.android.synthetic.main.item_class.view.*

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
class StudentChatVH(
    itemView: View,
    private val listener: (String) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun newInstance(parent: ViewGroup, listener: (String) -> Unit): ClassVH {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_class, parent, false)
            return ClassVH(view, listener)
        }
    }

    fun onBind(classVO: ClassVO) {
        itemView.tv_class_name.text = classVO.name
        val members = when (val count = classVO.countMembers) {
            0 -> ""
            1 -> itemView.context.resources.getString(R.string.main_teacher_members_count_one)
            else -> itemView.context.resources.getString(R.string.main_teacher_members_count_some, count)
        }
        itemView.tv_members_count.text = members
        itemView.setOnClickListener { listener.invoke(classVO.id) }

    }
}