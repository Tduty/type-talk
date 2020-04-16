package info.tduty.typetalk.view.task.hurryup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.HurryUpVO
import info.tduty.typetalk.data.model.WordamessVO
import info.tduty.typetalk.view.task.wordamess.PagerVH
import kotlinx.android.synthetic.main.item_task_card_content_word.view.*

class PageVH(
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

    fun onBind(hurryUpVO: HurryUpVO) {
       // itemView.tv_content.text = hurryUpVO.body
    }
}