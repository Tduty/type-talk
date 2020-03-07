package info.tduty.typetalk.socket

import android.content.Context
import com.google.gson.Gson
import info.tduty.typetalk.data.pref.UrlStorage
import info.tduty.typetalk.data.pref.UserDataHelper
import info.tduty.typetalk.domain.managers.AppLifecycleEventManager
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
    appLifecycleEventManager: AppLifecycleEventManager
) {

    private var client: SocketClient? = null
    private var appOnStartDisposable: Disposable? = null
    private var appOnStopDisposable: Disposable? = null

    init {
        appOnStartDisposable?.dispose()
        appOnStartDisposable = appLifecycleEventManager.onAppStart()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ connect() }, Timber::e)
        appOnStopDisposable?.dispose()
        appOnStopDisposable = appLifecycleEventManager.onAppStop()
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

    private fun createSocketClient(): SocketClient {
        return SocketClient(
            url = UrlStorage.getWebsocketUrl(),
            context = context,
            userDataHelper = userDataHelper,
            gson = gson
        )
    }
}
