package info.tduty.typetalk.view.teacher.manage.lessons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.LessonManageVO
import kotlinx.android.synthetic.main.item_lesson_manage.view.*

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class LessonManageVH(
    itemView: View,
    private val listener: (LessonManageVO) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun newInstance(parent: ViewGroup, listener: (LessonManageVO) -> Unit): LessonManageVH {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_lesson_manage, parent, false)
            return LessonManageVH(view, listener)
        }
    }

    fun onBind(lessonManageVO: LessonManageVO) {
        itemView.tv_lesson_number.text = itemView.context.getString(R.string.lesson_number, lessonManageVO.number)
        itemView.tv_title.text = lessonManageVO.name
        itemView.iv_icon.setImageResource(lessonManageVO.icon)
        setupCompleted(lessonManageVO)
        setupLockForClass(lessonManageVO.isLockForClass)
        itemView.setOnClickListener { listener.invoke(lessonManageVO) }

    }

    private fun setupCompleted(lessonManageVO: LessonManageVO) {
        when {
            lessonManageVO.isLockForClass -> {
                itemView.ppv_lesson_status.visibility = View.GONE
                itemView.tv_completed.setText(R.string.manage_lessons_can_open_lessons)
            }
            lessonManageVO.studentsCount == 0 -> {
                itemView.ppv_lesson_status.visibility = View.GONE
                itemView.tv_completed.visibility = View.GONE
            }
            lessonManageVO.completedCount == 0 -> {
                itemView.ppv_lesson_status.visibility = View.GONE
                itemView.tv_completed.text = itemView.context.getString(
                    R.string.completed_progress,
                    lessonManageVO.completedCount, lessonManageVO.studentsCount
                )
            }
            else -> {
                itemView.ppv_lesson_status.visibility = View.VISIBLE
                itemView.ppv_lesson_status.progress =
                    lessonManageVO.completedCount * 100 / lessonManageVO.studentsCount
                itemView.tv_completed.text = itemView.context.getString(
                    R.string.completed_progress,
                    lessonManageVO.completedCount, lessonManageVO.studentsCount
                )
            }
        }
    }

    private fun setupLockForClass(isLockForClass: Boolean) {
        itemView.iv_lock.visibility = if (isLockForClass) View.VISIBLE else View.GONE
    }
}
