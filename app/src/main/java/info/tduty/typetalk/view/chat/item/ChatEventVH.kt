package info.tduty.typetalk.view.chat.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.MessageVO
import kotlinx.android.synthetic.main.item_chat_event.view.*

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
class ChatEventVH(itemView: View) : ChatItemVH(itemView) {

    companion object {

        fun newInstance(inflater: LayoutInflater, parent: ViewGroup): ChatEventVH {
            val view = inflater.inflate(R.layout.item_chat_event, parent, false)
            return ChatEventVH(view)
        }
    }

    override fun onBind(messageVO: MessageVO) {
        itemView.tv_content.text = messageVO.message
    }
}
