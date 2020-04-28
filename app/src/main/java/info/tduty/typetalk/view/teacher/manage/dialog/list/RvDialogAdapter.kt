package info.tduty.typetalk.view.teacher.manage.dialog.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.DialogVO

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class RvDialogAdapter(private val listener: (String) -> Unit) : RecyclerView.Adapter<DialogVH>() {

    private val dialogs: MutableList<DialogVO> = ArrayList()

    fun setDialogs(dialogs: List<DialogVO>) {
        this.dialogs.clear()
        this.dialogs.addAll(dialogs)
        notifyDataSetChanged()
    }

    fun addDialog(dialog: DialogVO) {
        dialogs.add(dialog)
        notifyItemRangeChanged(dialogs.size - 1, 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DialogVH.newInstance(parent, listener)

    override fun getItemCount() = dialogs.size

    override fun onBindViewHolder(holder: DialogVH, position: Int) = holder.onBind(dialogs[position])
}
