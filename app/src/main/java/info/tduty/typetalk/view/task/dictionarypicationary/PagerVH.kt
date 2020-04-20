package info.tduty.typetalk.view.task.dictionarypicationary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
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
        setupProgressBar(true)
        val url = UrlStorage.getUrl() + dictionaryPictionaryVO.url
        Picasso.with(context).load(url).fetch(object : Callback {
            override fun onSuccess() {
                Picasso.with(context).load(url).into(itemView.iv_content)
                setupProgressBar(false)
            }

            override fun onError() {
                setupProgressBar(true)
            }
        })
    }

    private fun setupProgressBar(isShow: Boolean) {
        itemView.pb_progress.visibility = if (isShow) View.VISIBLE else View.GONE
    }

}