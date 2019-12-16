package info.tduty.typetalk.view.lesson

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.data.model.TaskVO

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */
class RvTasksAdapter(private val listener: (String, TaskType) -> Unit) : RecyclerView.Adapter<TaskVH>() {

    private val tasks: MutableList<TaskVO> = ArrayList()

    fun setTasks(tasks: List<TaskVO>) {
        this.tasks.clear()
        this.tasks.addAll(tasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TaskVH.newInstance(parent, listener)

    override fun onBindViewHolder(holder: TaskVH, position: Int) = holder.onBind(tasks[position])

    override fun getItemCount(): Int = tasks.size
}