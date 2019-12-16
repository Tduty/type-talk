package info.tduty.typetalk.view.lesson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.data.model.TaskVO
import kotlinx.android.synthetic.main.item_task.view.*

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */
class TaskVH(
    itemView: View,
    private val listener: (String, TaskType) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun newInstance(parent: ViewGroup, listener: (String, TaskType) -> Unit): TaskVH {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_task, parent, false)
            return TaskVH(view, listener)
        }
    }

    fun onBind(task: TaskVO) {
        itemView.iv_task_ic.setImageResource(task.icon)
        itemView.tv_task_title.text = task.title
        if (task.optional) setupChecked(task.checked)
        else hideChecked()
        itemView.setOnClickListener { listener.invoke(task.id, task.type) }
    }

    private fun setupChecked(checked: Boolean) {
        val resId = if (checked) R.drawable.ic_checkbox_complete_dark
        else R.drawable.ic_checkbox_empty_dark
        itemView.iv_task_status.visibility = View.VISIBLE
        itemView.iv_task_status.setImageResource(resId)
    }

    private fun hideChecked() {
        itemView.iv_task_status.visibility = View.GONE
    }
}
