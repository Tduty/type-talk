package info.tduty.typetalk.view.teacher.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.ClassVO

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
class RvClassesAdapter(private val listener: (String) -> Unit) : RecyclerView.Adapter<ClassVH>() {

    private val classes: MutableList<ClassVO> = ArrayList()

    fun setClasses(classes: List<ClassVO>) {
        this.classes.clear()
        this.classes.addAll(classes)
        notifyDataSetChanged()
    }

    fun addClass(classVO: ClassVO) {
        classes.add(classVO)
        notifyItemRangeChanged(classes.size - 1, 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ClassVH.newInstance(parent, listener)

    override fun getItemCount() = classes.size

    override fun onBindViewHolder(holder: ClassVH, position: Int) = holder.onBind(classes[position])
}