package info.tduty.typetalk.socket

import android.content.Context
import com.google.gson.Gson
import info.tduty.typetalk.data.event.Event
import info.tduty.typetalk.data.event.EventPayload
import info.tduty.typetalk.data.pref.UserDataHelper
import info.tduty.typetalk.domain.managers.SocketManager
import info.tduty.typetalk.domain.managers.SocketState
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.ConnectException
import java.util.*
import javax.net.ssl.SSLHandshakeException

/**
 * Created by Evgeniy Mezentsev on 04.03.2020.
 */
class SocketClient(
    private val url: String,
    private val context: Context,
    private val userDataHelper: UserDataHelper,
    private val socketManager: SocketManager,
    private val gson: Gson
) {

    private var socket: OkHttpSocketDelegate? = null
    private val callbacks = HashMap<EventPayload.Type, (Event) -> Unit>()

    fun isConnected(): Boolean = socket?.isConnected() == true

    fun connect() {
        val newSocket = OkHttpSocketDelegate(context, object : SocketListener {

            override fun onOpen(message: String) {
                socketManager.post(SocketState.OPEN)
            }

            override fun onTextMessage(text: String) {
                val event = gson.fromJson<Event>(text, Event::class.java)
                val type = EventPayload.Type.to(event.type)
                callbacks[type]?.invoke(event)
            }

            override fun onClosing(code: Int, reason: String) {
            }

            override fun onClosed(code: Int, reason: String) {
                socketManager.post(SocketState.CLOSE)
            }

            override fun onFailure(t: Throwable, code: Int, reason: String?) {
                if (code == OkHttpSocketDelegate.NORMAL_CLOSE_CODE ||
                    code == OkHttpSocketDelegate.NORMAL_CLOSE_LEAVED_DISPLAY_CODE) return
                when (t) {
                    is ConnectException,
                    is SSLHandshakeException -> Timber.e(t, "Code: $code, reason: $reason")
                    else -> connect()
                }
            }
        })
        newSocket.connect(
            url,
            userDataHelper.getSavedUser().id,
            userDataHelper.getSavedUser().surname
        )
        socket = newSocket
    }

    fun disconnect() {
        socket?.disconnect()
    }

    fun listenEvent(type: EventPayload.Type, eventBus: EventBusRx) {
        listenEvent(callbacks, type, eventBus)
    }

    fun pushEvent(event: Event) {
        val disposable = Observable.create<String> { subscriber ->
            val json = gson.toJson(event)
            val sent = socket?.send(json) ?: false
            subscriber.onNext(json)
            subscriber.onComplete()
            if (!sent) {
                subscriber.tryOnError(IllegalArgumentException("While push event. Event was not sent: $event"))
            }
        }
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({}, Timber::e)
    }

    private fun listenEvent(
        callbacks: MutableMap<EventPayload.Type, (Event) -> Unit>,
        type: EventPayload.Type,
        eventBus: EventBusRx
    ) {
        callbacks[type] = { event: Event ->
            try {
                event.eventPayload?.let {
                    eventBus.post(it)
                } ?: throw IllegalArgumentException("EventPayload is null")
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}
