package info.tduty.typetalk.utils.alert.vh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.utils.alert.AlertDialogItemsVO
import info.tduty.typetalk.utils.alert.AlertDialogVH
import kotlinx.android.synthetic.main.item_alert_dialog_items_incorrect_word.view.*

class AlertDialogItemsErrorVH(itemView: View) : RecyclerView.ViewHolder(itemView),
    AlertDialogVH {

    companion object {
        fun newInstance(parent: ViewGroup): AlertDialogItemsErrorVH {
            val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_alert_dialog_items_incorrect_word, parent, false)
            return AlertDialogItemsErrorVH(
                view
            )
        }
    }

    override fun onBind(alertDialogItemsVO: AlertDialogItemsVO) {
        itemView.tv_task_valid_word.text = alertDialogItemsVO.topWord
        itemView.tv_task_input_word.text = alertDialogItemsVO.bottomWord
    }
}
