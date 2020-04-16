package info.tduty.typetalk.view.task.dictionarypicationary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.DictionaryPictionaryVO
import info.tduty.typetalk.data.pref.UrlStorage
import kotlinx.android.synthetic.main.item_pager_task_dictionary_pictionary.view.*


class PagerVH(
    itemView: View,
    val context: Context
) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun newInstance(parent: ViewGroup, context: Context): PagerVH {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_pager_task_dictionary_pictionary, parent, false)
            return PagerVH(view, context)
        }
    }

    fun onBind(dictionaryPictionaryVO: DictionaryPictionaryVO) {
        Picasso.with(context)
            .load(UrlStorage.getUrl() + dictionaryPictionaryVO.url)
            .into(itemView.iv_content)
    }

}