package info.tduty.typetalk.view.dictionary.item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.DictionaryVO
import info.tduty.typetalk.view.dictionary.adapter.DictionaryTopicAdapter
import kotlinx.android.synthetic.main.item_dictionary_section.view.*

class DictionaryListVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        private var context: Context? = null

        fun newInstance(parent: ViewGroup): DictionaryListVH {
            context = parent.context
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_dictionary_section, parent, false)
            return DictionaryListVH(view)
        }
    }

    fun onBind(dictionaryVO: DictionaryVO) {
        if (dictionaryVO.title != null && dictionaryVO.title.isNotEmpty()) {
            itemView.titleTopic.text = dictionaryVO.title
        } else {
            itemView.titleTopicContainer.visibility = View.GONE
        }

        val adapter = DictionaryTopicAdapter()
        adapter.setWords(dictionaryVO.vocabularies)

        itemView.wordTopicList.layoutManager = LinearLayoutManager(context)
        itemView.wordTopicList.adapter = adapter
    }
}
