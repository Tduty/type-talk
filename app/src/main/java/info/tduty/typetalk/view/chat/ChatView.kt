package info.tduty.typetalk.view.chat

import info.tduty.typetalk.data.model.CorrectionVO
import info.tduty.typetalk.data.model.MessageVO

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */
interface ChatView {

    fun setToolbarTitle(title: String)

    fun setToolbarIcon(icon: Int)

    fun hideUserInput()

    fun clearUserInput()

    fun showTeacherMenu()

    fun addEventToStart(messageVO: MessageVO)

    fun addEvent(messageVO: MessageVO)

    fun addEvents(messageVO: List<MessageVO>)

    fun setEvents(messageVO: List<MessageVO>)

    fun updateCorrection(correctionVO: CorrectionVO)

    fun showCorrectionState(title: String, body: String, type: CorrectionVO.AdditionalType)

    fun hideCorrectionState()

    fun showMessageActionDialog(messageVO: MessageVO)

    fun showCountMessages(count: Int, foldingAnimate: Boolean = false)

    fun showErrorAboutRussianSymbols()
}