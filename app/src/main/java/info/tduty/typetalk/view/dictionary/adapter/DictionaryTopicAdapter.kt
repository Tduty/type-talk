package info.tduty.typetalk.view.dictionary.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.VocabularyVO
import info.tduty.typetalk.view.dictionary.item.DictionaryTopicVH

class DictionaryTopicAdapter : RecyclerView.Adapter<DictionaryTopicVH>(){

    private val vocabularyVO: MutableList<VocabularyVO> = ArrayList()

    fun setWords(vocabularyVO: List<VocabularyVO>) {
        this.vocabularyVO.clear()
        this.vocabularyVO.addAll(vocabularyVO)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DictionaryTopicVH.newInstance(parent)

    override fun onBindViewHolder(holder: DictionaryTopicVH, position: Int) = holder.onBind(vocabularyVO[position])

    override fun getItemCount(): Int = vocabularyVO.size
}
