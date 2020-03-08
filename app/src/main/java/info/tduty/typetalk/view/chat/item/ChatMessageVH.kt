package info.tduty.typetalk.view.chat.item

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.MessageVO
import kotlinx.android.synthetic.main.item_chat_event.view.*
import kotlinx.android.synthetic.main.item_chat_message_foreign.view.*

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
class ChatMessageVH(itemView: View) : ChatItemVH(itemView) {

    companion object {

        fun newInstance(inflater: LayoutInflater, isMy: Boolean, parent: ViewGroup): ChatMessageVH {
            val resId = if (isMy) R.layout.item_chat_message_my
            else R.layout.item_chat_message_foreign
            val view = inflater.inflate(resId, parent, false)
            return ChatMessageVH(view)
        }
    }

    override fun onBind(messageVO: MessageVO) {
        if (messageVO.showSender) showSender(messageVO.senderName, messageVO.avatar)
        else hideSender()
        itemView.tv_content.text = messageVO.message
    }

    private fun showSender(name: String, iconRes: Int) {
        itemView.tv_title.visibility = VISIBLE
        itemView.tv_title.text = name
        itemView.iv_user.visibility = VISIBLE
        itemView.iv_user.setImageResource(iconRes)
    }

    private fun hideSender() {
        itemView.tv_title.visibility = GONE
        itemView.iv_user.visibility = GONE
    }
}
