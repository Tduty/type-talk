package info.tduty.typetalk.view.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.LessonVO

/**
 * Created by Evgeniy Mezentsev on 2019-11-20.
 */
class RvLessonsAdapter(private val listener: (String) -> Unit) : RecyclerView.Adapter<LessonVH>() {

    private val lessons: MutableList<LessonVO> = ArrayList()

    fun setLessons(lessons: List<LessonVO>) {
        this.lessons.clear()
        this.lessons.addAll(lessons)
        notifyDataSetChanged()
    }

    fun addLesson(lesson: LessonVO) {
        lessons.add(lesson)
        notifyItemRangeChanged(lessons.size - 1, 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LessonVH.newInstance(parent, listener)

    override fun getItemCount() = lessons.size

    override fun onBindViewHolder(holder: LessonVH, position: Int) = holder.onBind(lessons[position])
}
