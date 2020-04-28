package info.tduty.typetalk.view.teacher.manage.dialog.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.DialogVO
import kotlinx.android.synthetic.main.item_dialog.view.*

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class DialogVH(
    itemView: View,
    private val listener: (String) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun newInstance(parent: ViewGroup, listener: (String) -> Unit): DialogVH {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_dialog, parent, false)
            return DialogVH(view, listener)
        }
    }

    fun onBind(dialog: DialogVO) {
        itemView.tv_title.text = dialog.title
        itemView.setOnClickListener { listener.invoke(dialog.chatId) }
    }
}