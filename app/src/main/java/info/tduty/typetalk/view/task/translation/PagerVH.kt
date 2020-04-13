package info.tduty.typetalk.view.task.translation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.TranslationVO
import kotlinx.android.synthetic.main.item_task_card_content_word.view.*

class PagerVH(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun newInstance(parent: ViewGroup): PagerVH {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_task_card_content_word, parent, false)
            return PagerVH(view)
        }
    }

    fun onBind(translationVO: TranslationVO) {
        itemView.tv_content.text = translationVO.word
    }
}