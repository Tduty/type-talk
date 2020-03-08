package info.tduty.typetalk.view.chat

import info.tduty.typetalk.data.model.MessageVO

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */
interface ChatView {

    fun setToolbarTitle(title: String)

    fun setToolbarIcon(icon: String)

    fun addEvent(messageVO: MessageVO)

    fun addEvents(messageVO: List<MessageVO>)

    fun setEvents(messageVO: List<MessageVO>)
}