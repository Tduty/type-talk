package info.tduty.typetalk.view.chat.item

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.CorrectionVO
import info.tduty.typetalk.data.model.MessageVO
import kotlinx.android.synthetic.main.item_chat_message_foreign.view.*

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
class ChatMessageVH(
    itemView: View,
    private val listener: (MessageVO) -> Unit
) : ChatItemVH(itemView) {

    companion object {

        fun newInstance(
            inflater: LayoutInflater, isMy: Boolean,
            parent: ViewGroup, listener: (MessageVO) -> Unit
        ): ChatMessageVH {
            val resId = if (isMy) R.layout.item_chat_message_my
            else R.layout.item_chat_message_foreign
            val view = inflater.inflate(resId, parent, false)
            return ChatMessageVH(view, listener)
        }
    }

    override fun onBind(messageVO: MessageVO) {
        if (messageVO.showSender && !messageVO.isMy) {
            showSender(messageVO.senderName, messageVO.avatar)
        } else if (!messageVO.isMy) hideSender()
        itemView.tv_message.text = messageVO.message
        updateCorrection(messageVO.isMy, messageVO.correction)
        itemView.setOnClickListener { listener.invoke(messageVO) }
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

    private fun updateCorrection(isMy: Boolean, correctionVO: CorrectionVO?) {
        if (correctionVO != null && correctionVO.additional.isNotBlank()) {
            if (isMy) itemView.ll_bubble.setBackgroundResource(R.drawable.bg_bubble_mistake)
            else itemView.ll_bubble.setBackgroundResource(R.drawable.bg_bubble_foreign_mistake)
            itemView.tv_comment_title.visibility = VISIBLE
            itemView.tv_correction.visibility = VISIBLE
            val titleResId = when (correctionVO.type) {
                CorrectionVO.AdditionalType.COMMENT -> R.string.chat_message_teacher_comment
                CorrectionVO.AdditionalType.CORRECTION -> R.string.chat_message_teacher_correction
                CorrectionVO.AdditionalType.NONE -> R.string.chat_message_teacher_another
            }
            itemView.tv_comment_title.setText(titleResId)
            itemView.tv_correction.text = correctionVO.additional
        } else {
            if (isMy) itemView.ll_bubble.setBackgroundResource(R.drawable.bg_bubble)
            else itemView.ll_bubble.setBackgroundResource(R.drawable.bg_bubble_foreign)
            itemView.tv_comment_title.visibility = GONE
            itemView.tv_correction.visibility = GONE
        }
    }
}
