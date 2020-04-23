package info.tduty.typetalk.view.teacher.manage.tasks

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.TaskManageVO

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class RvTaskManageAdapter(private val listener: (TaskManageVO) -> Unit) : RecyclerView.Adapter<TaskManageVH>() {

    private val tasks: MutableList<TaskManageVO> = ArrayList()

    fun setTasks(tasks: List<TaskManageVO>) {
        this.tasks.clear()
        this.tasks.addAll(tasks)
        notifyDataSetChanged()
    }

    fun addTask(task: TaskManageVO) {
        tasks.add(task)
        notifyItemRangeChanged(tasks.size - 1, 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TaskManageVH.newInstance(parent, listener)

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskManageVH, position: Int) = holder.onBind(tasks[position])
}
