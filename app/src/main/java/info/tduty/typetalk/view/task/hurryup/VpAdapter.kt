package info.tduty.typetalk.view.task.hurryup

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.HurryUpVO
import info.tduty.typetalk.data.model.WordamessVO
import info.tduty.typetalk.view.task.wordamess.PagerVH
import java.util.*

class VpAdapter : RecyclerView.Adapter<PagerVH>() {

    private var hurryUpList: List<HurryUpVO> = Collections.emptyList()

    fun setupHurryUpList(hurryUpList: List<HurryUpVO>) {
        this.hurryUpList = hurryUpList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH = PagerVH.newInstance(parent)

    override fun getItemCount(): Int = hurryUpList.size

    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.onBind(hurryUpList[position])
}