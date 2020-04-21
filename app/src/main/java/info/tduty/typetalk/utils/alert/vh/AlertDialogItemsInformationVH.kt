package info.tduty.typetalk.utils.alert.vh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.utils.alert.AlertDialogItemsVO
import info.tduty.typetalk.utils.alert.AlertDialogVH
import kotlinx.android.synthetic.main.item_alert_dialog_items_information.view.*

class AlertDialogItemsInformationVH(itemView: View) : RecyclerView.ViewHolder(itemView),
    AlertDialogVH {

    companion object {
        fun newInstance(parent: ViewGroup): AlertDialogItemsInformationVH {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_alert_dialog_items_information, parent, false)
            return AlertDialogItemsInformationVH(
                view
            )
        }
    }

    override fun onBind(alertDialogItemsVO: AlertDialogItemsVO) {
        itemView.tv_information.text = alertDialogItemsVO.topWord
    }
}