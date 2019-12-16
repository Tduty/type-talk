package info.tduty.typetalk.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.ExpectedVO
import info.tduty.typetalk.data.model.LessonVO
import kotlinx.android.synthetic.main.item_lesson.view.*
import kotlinx.android.synthetic.main.item_lesson_expected.view.*

/**
 * Created by Evgeniy Mezentsev on 2019-11-20.
 */
class LessonVH(
    itemView: View,
    private val listener: (String) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun newInstance(parent: ViewGroup, listener: (String) -> Unit): LessonVH {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_lesson, parent, false)
            return LessonVH(view, listener)
        }
    }

    fun onBind(lessonVO: LessonVO) {
        val numberRes = R.string.main_chat_item_lesson_number
        itemView.tv_lesson_nmb.text = itemView.context.getString(numberRes, lessonVO.number)
        itemView.tv_lesson_title.text = lessonVO.title
        itemView.iv_lesson_ic.setImageResource(lessonVO.icon)
        itemView.iv_lesson_checkbox.setImageResource(lessonVO.status.icon)
        itemView.tv_lesson_status.text = lessonVO.status.name
        itemView.tv_lesson_content.text = lessonVO.content
        setupExpected(lessonVO.expectedList)
        itemView.setOnClickListener { listener.invoke(lessonVO.id) }

    }

    private fun setupExpected(list: List<ExpectedVO>) {
        list.getOrNull(0)?.let {
            itemView.item_lesson_expected_one.iv_expected_ic.setImageResource(it.icon)
            itemView.item_lesson_expected_one.tv_lesson_expected.text = it.title
            itemView.item_lesson_expected_one.visibility = View.VISIBLE
        } ?: hideExpectedOne()
        list.getOrNull(1)?.let {
            itemView.item_lesson_expected_two.iv_expected_ic.setImageResource(it.icon)
            itemView.item_lesson_expected_two.tv_lesson_expected.text = it.title
            itemView.item_lesson_expected_two.visibility = View.VISIBLE
        } ?: hideExpectedTwo()
        list.getOrNull(2)?.let {
            itemView.item_lesson_expected_three.iv_expected_ic.setImageResource(it.icon)
            itemView.item_lesson_expected_three.tv_lesson_expected.text = it.title
            itemView.item_lesson_expected_three.visibility = View.VISIBLE
        } ?: hideExpectedThree()
    }

    private fun hideExpectedOne() {
        itemView.item_lesson_expected_one.visibility = View.GONE
    }

    private fun hideExpectedTwo() {
        itemView.item_lesson_expected_two.visibility = View.GONE
    }

    private fun hideExpectedThree() {
        itemView.item_lesson_expected_three.visibility = View.GONE
    }
}
