package info.tduty.typetalk.view.task.hurryup

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.HurryUpVO
import kotlinx.android.synthetic.main.fragment_wordamess.*
import kotlinx.android.synthetic.main.item_pager_task_hurry_up.view.*
import java.util.ArrayList

class PagerVH(
    itemView: View,
    val clickListener: ((HurryUpVO) -> Unit)? = null
) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun newInstance(parent: ViewGroup, clickListener: ((HurryUpVO) -> Unit)? = null): PagerVH {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_pager_task_hurry_up, parent, false)
            return PagerVH(view, clickListener)
        }
    }

    fun onBind(hurryUpVO: HurryUpVO) {
        val wordList = ArrayList(hurryUpVO.anyTranslates)
        wordList.add(hurryUpVO.translate)

        itemView.fl_chip.clearAll()

        wordList.forEach {
            itemView.fl_chip.addChip(it) { isSelected: Boolean, body: String ->
                if (body == hurryUpVO.translate) {
                    clickListener?.invoke(hurryUpVO)
                }
            }
        }
    }
}