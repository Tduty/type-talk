package info.tduty.typetalk.view.teacher.manage.lessons

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.LessonManageVO

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class RvLessonManageAdapter(private val listener: (LessonManageVO) -> Unit) : RecyclerView.Adapter<LessonManageVH>() {

    private val lessons: MutableList<LessonManageVO> = ArrayList()

    fun setLessons(lessons: List<LessonManageVO>) {
        this.lessons.clear()
        this.lessons.addAll(lessons)
        notifyDataSetChanged()
    }

    fun addLesson(lesson: LessonManageVO) {
        lessons.add(lesson)
        notifyItemRangeChanged(lessons.size - 1, 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LessonManageVH.newInstance(parent, listener)

    override fun getItemCount() = lessons.size

    override fun onBindViewHolder(holder: LessonManageVH, position: Int) = holder.onBind(lessons[position])
}
