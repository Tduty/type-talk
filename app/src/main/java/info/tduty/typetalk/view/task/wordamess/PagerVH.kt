package info.tduty.typetalk.view.task.wordamess

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.WordamessVO
import kotlinx.android.synthetic.main.item_pager_task_translation.view.*

/**
 * Created by Evgeniy Mezentsev on 15.04.2020.
 */
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

    fun onBind(wordamessVO: WordamessVO) {
        itemView.tv_content.text = wordamessVO.body
    }
}