package info.tduty.typetalk.socket

import android.content.Context
import com.google.gson.Gson
import info.tduty.typetalk.data.event.Event
import info.tduty.typetalk.data.event.EventPayload
import info.tduty.typetalk.data.pref.PreferencesHelper
import info.tduty.typetalk.data.pref.UserDataHelper
import info.tduty.typetalk.data.pref.UserDataSharedPreferencesHelper
import io.reactivex.Observable
import timber.log.Timber
import java.lang.IllegalArgumentException
import java.util.HashMap

/**
 * Created by Evgeniy Mezentsev on 04.03.2020.
 */
class SocketClient(
    private val url: String,
    private val context: Context,
    private val userDataHelper: UserDataHelper,
    private val gson: Gson
) {

    private var socket: OkHttpSocketDelegate? = null
    private val callbacks = HashMap<EventPayload.Type, (Event) -> Unit>()

    fun isConnected(): Boolean = socket?.isConnected() == true

    fun connect() {
        val newSocket = OkHttpSocketDelegate(context, object : SocketListener {

            override fun onOpen(message: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextMessage(text: String) {
                val event = gson.fromJson<Event>(text, Event::class.java)
                val type = EventPayload.Type.to(event.type)
                callbacks[type]?.invoke(event)
            }

            override fun onClosing(code: Int, reason: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onClosed(code: Int, reason: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFailure(t: Throwable, code: Int, reason: String?) {
                connect()
            }
        })
        newSocket.connect(url, userDataHelper.getSavedUser().id)
        socket = newSocket
    }

    fun disconnect() {
        socket?.disconnect()
    }

    fun listenEvent(type: EventPayload.Type, eventBus: EventBusRx) {
        listenEvent(callbacks, type, eventBus)
    }

    fun pushEvent(event: Event): Observable<String> {
        return Observable.create { subscriber ->
            val json = gson.toJson(event)
            val sent = socket?.send(json) ?: false
            if (!sent) {
                subscriber.tryOnError(IllegalArgumentException("While push event. Event was not sent: $event"))
            }
        }
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
