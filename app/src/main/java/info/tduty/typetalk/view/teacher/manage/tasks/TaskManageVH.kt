package info.tduty.typetalk.view.teacher.manage.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.LessonManageVO
import info.tduty.typetalk.data.model.TaskManageVO
import info.tduty.typetalk.view.teacher.manage.lessons.LessonManageVH
import kotlinx.android.synthetic.main.item_lesson_manage.view.*

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class TaskManageVH(
    itemView: View,
    private val listener: (TaskManageVO) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun newInstance(parent: ViewGroup, listener: (TaskManageVO) -> Unit): TaskManageVH {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_task_manage, parent, false)
            return TaskManageVH(view, listener)
        }
    }

    fun onBind(task: TaskManageVO) {
        itemView.tv_title.text = task.title
        itemView.iv_icon.setImageResource(task.type.imageSrc)
        setupCompleted(task.studentsCount, task.completedCount)
        itemView.setOnClickListener { listener.invoke(task) }
    }

    private fun setupCompleted(all: Int, completed: Int) {
        if (all != 0) {
            itemView.tv_completed.text = itemView.context.getString(R.string.completed_progress, completed, all)
            if (completed != 0) {
                itemView.ppv_lesson_status.visibility = View.VISIBLE
                itemView.ppv_lesson_status.progress = completed * 100 / all
            } else itemView.ppv_lesson_status.visibility = View.GONE
        } else {
            itemView.tv_completed.visibility = View.GONE
            itemView.ppv_lesson_status.visibility = View.GONE
        }
    }
}