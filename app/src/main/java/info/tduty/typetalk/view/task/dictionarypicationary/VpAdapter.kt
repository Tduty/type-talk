package info.tduty.typetalk.view.task.dictionarypicationary

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.DictionaryPictionaryVO
import java.util.*

class VpAdapter(val context: Context): RecyclerView.Adapter<PagerVH>() {

    private var dictionaryPictionaryList: List<DictionaryPictionaryVO> = Collections.emptyList()

    fun setupDictionaryPictionaryList(dictionaryPictionaryList: List<DictionaryPictionaryVO>) {
        this.dictionaryPictionaryList = dictionaryPictionaryList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH = PagerVH.newInstance(parent, context)

    override fun getItemCount(): Int = dictionaryPictionaryList.size

    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.onBind(dictionaryPictionaryList[position])
}