package info.tduty.typetalk.view.dictionary.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.VocabularyVO
import kotlinx.android.synthetic.main.item_dictionary_row.view.*

class DictionaryTopicVH (itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup): DictionaryTopicVH {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_dictionary_row, parent, false)
            return DictionaryTopicVH(
                view
            )
        }
    }

    fun onBind(vocabularyVO: VocabularyVO) {
        itemView.wordField.text = vocabularyVO.word
        itemView.translationField.text = vocabularyVO.translation

        if (vocabularyVO.transcription != null && vocabularyVO.transcription!!.isNotEmpty()) {
            itemView.transcriptionField.text = vocabularyVO.transcription
        } else {
            itemView.transcriptionField.visibility = View.GONE
        }
    }
}
