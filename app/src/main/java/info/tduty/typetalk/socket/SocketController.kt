package info.tduty.typetalk.socket

import android.content.Context
import com.google.gson.Gson
import info.tduty.typetalk.data.event.Event
import info.tduty.typetalk.data.event.EventPayload.Type.*
import info.tduty.typetalk.data.event.payload.*
import info.tduty.typetalk.data.pref.UrlStorage
import info.tduty.typetalk.data.pref.UserDataHelper
import info.tduty.typetalk.domain.managers.AppLifecycleEventManager
import info.tduty.typetalk.domain.managers.SocketManager
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 04.03.2020.
 */
class SocketController(
    private val context: Context,
    private val gson: Gson,
    private val userDataHelper: UserDataHelper,
    appLifecycleEventManager: AppLifecycleEventManager,
    private val socketManager: SocketManager,
    private val socketEventListener: SocketEventListener
) {

    private var client: SocketClient? = null
    private var appOnStartDisposable: Disposable? = null
    private var appOnStopDisposable: Disposable? = null

    init {
        appOnStartDisposable?.dispose()
        appOnStartDisposable = appLifecycleEventManager.onAppCreate()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ connect() }, Timber::e)
        appOnStopDisposable?.dispose()
        appOnStopDisposable = appLifecycleEventManager.onAppDestroy()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ disconnect() }, Timber::e)
    }

    fun connect() {
        Timber.d("[Socket] App start connect to socket")
        if (client == null) client = createSocketClient()
        client?.let {
            if (!it.isConnected()) it.connect()
        }
    }

    fun disconnect() {
        Timber.d("[Socket] App start disconnect to socket")
        client?.disconnect()
    }

    fun sendMessageNew(messageNew: MessageNewPayload) {
        try {
            client?.pushEvent(Event(MESSAGE_NEW.string, messageNew))
        } catch (ex: Exception) {
            Timber.e(ex, "Error sending event $messageNew")
        }
    }

    fun sendCorrection(correction: CorrectionPayload) {
        try {
            client?.pushEvent(Event(CORRECTION.string, correction))
        } catch (ex: Exception) {
            Timber.e(ex, "Error sending event $correction")
        }
    }

    fun sendLesson(lesson: LessonPayload) {
        try {
            client?.pushEvent(Event(LESSON.string, lesson))
        } catch (ex: Exception) {
            Timber.e(ex, "Error sending event $lesson")
        }
    }

    fun sendTyping(typing: TypingPayload) {
        try {
            client?.pushEvent(Event(TYPING.string, typing))
        } catch (ex: Exception) {
            Timber.e(ex, "Error sending event $typing")
        }
    }

    fun sendCompleteTask(typing: CompleteTaskPayload) {
        try {
            client?.pushEvent(Event(TASK.string, typing))
        } catch (ex: Exception) {
            Timber.e(ex, "Error sending event $typing")
        }
    }

    fun sendUpdateStatusLesson(typing: LessonProgressPayload) {
        try {
            client?.pushEvent(Event(LESSON_PROGRESS.string, typing))
        } catch (ex: Exception) {
            Timber.e(ex, "Error sending event $typing")
        }
    }


    private fun createSocketClient(): SocketClient {
        val client =  SocketClient(
            url = UrlStorage.getWebsocketUrl(),
            context = context,
            userDataHelper = userDataHelper,
            socketManager = socketManager,
            gson = gson
        )
        socketEventListener.listenEvents(client)
        return client
    }
}
