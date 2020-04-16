package info.tduty.typetalk.view.task.wordamess

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.WordamessVO
import java.util.*

/**
 * Created by Evgeniy Mezentsev on 16.04.2020.
 */
class ViewPagerAdapter: RecyclerView.Adapter<PagerVH>() {

    private var wordamessList: List<WordamessVO> = Collections.emptyList()

    fun setupWordamessList(wordamessList: List<WordamessVO>) {
        this.wordamessList = wordamessList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH = PagerVH.newInstance(parent)

    override fun getItemCount(): Int = wordamessList.size

    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.onBind(wordamessList[position])
}