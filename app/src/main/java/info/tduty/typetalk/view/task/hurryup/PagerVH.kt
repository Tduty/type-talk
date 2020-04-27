package info.tduty.typetalk.view.task.hurryup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.HurryUpVO
import info.tduty.typetalk.utils.view.ColorChipLayout
import kotlinx.android.synthetic.main.item_chip.view.*
import kotlinx.android.synthetic.main.item_pager_task_hurry_up.view.*

class PagerVH(
    itemView: View,
    val clickListener: ((selectWord: String, HurryUpVO) -> Unit)? = null
) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun newInstance(parent: ViewGroup, clickListener: ((selectWord: String, HurryUpVO) -> Unit)? = null): PagerVH {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_pager_task_hurry_up, parent, false)
            return PagerVH(view, clickListener)
        }
    }

    fun onBind(hurryUpVO: HurryUpVO) {
        val wordList = getPayloadForChipLayout(hurryUpVO).shuffled()
        itemView.fl_chip.singleClick = true
        itemView.fl_chip.clearAll()

        itemView.tv_chip.text = hurryUpVO.word

        wordList.forEach {
            itemView.fl_chip.addChip(it.first, it.second) { isSelected: Boolean, body: String ->
                clickListener?.invoke(body, hurryUpVO)
            }
        }
    }

    private fun getPayloadForChipLayout(hurryUpVO: HurryUpVO): List<Pair<String, Map<ColorChipLayout.TypeColor, Int>>> {
        val wordList = mutableListOf<Pair<String, Map<ColorChipLayout.TypeColor, Int>>>()

        hurryUpVO.anyTranslates.forEach {
            val mapColor = HashMap<ColorChipLayout.TypeColor, Int>()
            mapColor[ColorChipLayout.TypeColor.SELECTED] = R.color.cosmos
            mapColor[ColorChipLayout.TypeColor.UNSELECETED] = R.color.white
            mapColor[ColorChipLayout.TypeColor.BACKGROUND] = R.color.white
            wordList.add(Pair(it, mapColor))
        }

        val mapColor = HashMap<ColorChipLayout.TypeColor, Int>()
        mapColor[ColorChipLayout.TypeColor.SELECTED] = R.color.chateau_green
        mapColor[ColorChipLayout.TypeColor.UNSELECETED] = R.color.white
        mapColor[ColorChipLayout.TypeColor.BACKGROUND] = R.color.white
        wordList.add(Pair(hurryUpVO.translate, mapColor))

        return wordList
    }
}