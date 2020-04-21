package info.tduty.typetalk.utils.alert

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.utils.alert.vh.AlertDialogItemsErrorVH
import info.tduty.typetalk.utils.alert.vh.AlertDialogItemsImageVH
import info.tduty.typetalk.utils.alert.vh.AlertDialogItemsInformationVH

class AlertDialogItemsRVAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val alertDialogItemsVO: MutableList<AlertDialogItemsVO> = ArrayList()

    enum class ViewType(var type: Int) {
        ERROR(0),
        INFORMATION(1),
        IMAGE(2);
    }

    fun setAlertDialogItemsVO(alertDialogItemsVO: List<AlertDialogItemsVO>) {
        this.alertDialogItemsVO.clear()
        this.alertDialogItemsVO.addAll(alertDialogItemsVO)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val item = alertDialogItemsVO[position]
        return when (item.type) {
            TypeAlertItem.ERROR -> ViewType.ERROR.type
            TypeAlertItem.INFO -> ViewType.INFORMATION.type
            TypeAlertItem.IMAGE -> ViewType.IMAGE.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       when(viewType) {
           0 -> return AlertDialogItemsErrorVH.newInstance(parent)
           1 -> return AlertDialogItemsInformationVH.newInstance(parent)
           2 -> return AlertDialogItemsImageVH.newInstance(parent)
       }
       return AlertDialogItemsErrorVH.newInstance(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = (holder as AlertDialogVH).onBind(alertDialogItemsVO[position])

    override fun getItemCount(): Int = alertDialogItemsVO.size
}
