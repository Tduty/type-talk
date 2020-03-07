package info.tduty.typetalk.view.chat.item

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.MessageVO

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
abstract class ChatItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBind(messageVO: MessageVO)
}
