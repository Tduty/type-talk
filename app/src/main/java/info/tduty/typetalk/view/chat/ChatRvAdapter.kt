package info.tduty.typetalk.view.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.CorrectionVO
import info.tduty.typetalk.data.model.MessageVO
import info.tduty.typetalk.view.chat.item.ChatEventVH
import info.tduty.typetalk.view.chat.item.ChatItemVH
import info.tduty.typetalk.view.chat.item.ChatMessageVH

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
class ChatRvAdapter(
    private val listener: (MessageVO) -> Unit
) : RecyclerView.Adapter<ChatItemVH>() {

    companion object {
        private const val CHAT_EVENT = 2
        private const val CHAT_MESSAGE_MY = 3
        private const val CHAT_MESSAGE_FOREIGN = 4
    }

    private val events: MutableList<MessageVO> = ArrayList()

    fun setEvents(events: List<MessageVO>) {
        this.events.clear()
        this.events.addAll(events)
        notifyDataSetChanged()
    }

    fun addEvents(events: List<MessageVO>) {
        this.events.addAll(events)
        notifyDataSetChanged()
    }

    fun addEventToStart(event: MessageVO) {
        val events = ArrayList<MessageVO>()
        events.addAll(this.events)
        this.events.clear()
        this.events.add(event)
        this.events.addAll(events)
        notifyDataSetChanged()
    }

    fun addEvent(event: MessageVO) {
        events.add(event)
        notifyItemRangeChanged(events.size - 1, 1)
    }

    fun updateCorrection(correctionVO: CorrectionVO) {
        val index = events.indexOfFirst { it.id == correctionVO.syncId }
        if (index == -1) return
        events[index].correction = correctionVO
        notifyItemChanged(index)
    }

    override fun getItemViewType(position: Int): Int {
        return when (events[position].type) {
            MessageVO.Type.EVENT -> CHAT_EVENT
            MessageVO.Type.MESSAGE -> if (events[position].isMy) CHAT_MESSAGE_MY
            else CHAT_MESSAGE_FOREIGN
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemVH {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CHAT_EVENT -> ChatEventVH.newInstance(inflater, parent)
            CHAT_MESSAGE_MY -> ChatMessageVH.newInstance(inflater, true, parent, listener)
            CHAT_MESSAGE_FOREIGN -> ChatMessageVH.newInstance(inflater, false, parent, listener)
            else -> ChatEventVH.newInstance(inflater, parent)
        }
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ChatItemVH, position: Int) {
        holder.onBind(events[position])
    }
}