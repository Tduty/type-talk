package info.tduty.typetalk.socket

/**
 * Created by Evgeniy Mezentsev on 2020-01-06.
 */
interface SocketListener {

    fun onOpen(message: String)

    fun onTextMessage(text: String)

    fun onClosing(code: Int, reason: String)

    fun onClosed(code: Int, reason: String)

    fun onFailure(t: Throwable, code: Int, reason: String?)
}