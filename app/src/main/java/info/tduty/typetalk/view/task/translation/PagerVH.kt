package info.tduty.typetalk.view.task.translation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.TranslationVO
import info.tduty.typetalk.view.task.StateInputWord
import kotlinx.android.synthetic.main.item_pager_task_translation.view.*

class PagerVH(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun newInstance(parent: ViewGroup): PagerVH {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_pager_task_translation, parent, false)
            return PagerVH(view)
        }
    }

    fun onBind(translationVO: TranslationVO) {
        itemView.tv_content.text = translationVO.word
        itemView.iv_right_top_corner.visibility = if (translationVO.isValid()) View.VISIBLE else View.GONE
    }
}