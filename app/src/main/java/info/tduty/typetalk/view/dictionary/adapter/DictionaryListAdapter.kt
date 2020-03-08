package info.tduty.typetalk.view.dictionary.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.DictionaryVO
import info.tduty.typetalk.view.dictionary.item.DictionaryListVH

class DictionaryListAdapter : RecyclerView.Adapter<DictionaryListVH>(){

    private val dictionaryVO: MutableList<DictionaryVO> = ArrayList()

    fun setDictionaryList(dictionaryVO: List<DictionaryVO>) {
        this.dictionaryVO.clear()
        this.dictionaryVO.addAll(dictionaryVO)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DictionaryListVH.newInstance(parent)

    override fun onBindViewHolder(holder: DictionaryListVH, position: Int) = holder.onBind(dictionaryVO[position])

    override fun getItemCount(): Int = dictionaryVO.size
}
