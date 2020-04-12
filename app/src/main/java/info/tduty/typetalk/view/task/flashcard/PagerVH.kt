package info.tduty.typetalk.view.task.flashcard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.FlashcardVO
import kotlinx.android.synthetic.main.item_pager_task_flashcard.view.*

class PagerVH(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun newInstance(parent: ViewGroup): PagerVH {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_pager_task_flashcard, parent, false)
            return PagerVH(view)
        }
    }

    fun onBind(flashcardVO: FlashcardVO) {
        itemView.tv_front_word.text = flashcardVO.frontWord
        itemView.tv_back_word.text = flashcardVO.backWord
        itemView.setOnClickListener {
            itemView.efv_lessons.flipTheView(true)
        }
    }
}