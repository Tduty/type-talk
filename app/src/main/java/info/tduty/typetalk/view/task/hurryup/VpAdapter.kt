package info.tduty.typetalk.view.task.hurryup

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.HurryUpVO
import java.util.*

class VpAdapter : RecyclerView.Adapter<PagerVH>() {

    private var clickListener: ((selectedWord: String, HurryUpVO) -> Unit)? = null
    private var hurryUpList: List<HurryUpVO> = Collections.emptyList()

    fun setupHurryUpList(hurryUpList: List<HurryUpVO>, clickListener: ((selectedWord: String, HurryUpVO) -> Unit)? = null) {
        this.clickListener = clickListener
        this.hurryUpList = hurryUpList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH = PagerVH.newInstance(parent, clickListener)

    override fun getItemCount(): Int = hurryUpList.size

    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.onBind(hurryUpList[position])
}