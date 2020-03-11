package info.tduty.typetalk.socket

import android.content.Context
import okhttp3.*
import okio.ByteString
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by Evgeniy Mezentsev on 2020-01-06.
 */
class OkHttpSocketDelegate(
    private val context: Context,
    private val listener: SocketListener
) : WebSocketListener() {

    companion object {
        const val CONNECT_TIMEOUT_MS = 60000L
        const val READ_TIMEOUT_MS = 60000L
        const val WRITE_TIMEOUT_MS = 60000L
        const val PING_INTERVAL_MS = 10000L
    }

    private var okHttpClient: OkHttpClient? = null
    private var runningSocket: WebSocket? = null

    fun connect(endpoint: String, uuid: String) {
        connectOkHttpWebSocket(endpoint, uuid)
    }

    fun disconnect(code: Int = 0): Boolean {
        okHttpClient?.dispatcher()?.cancelAll()
        return runningSocket?.close(code, "Socket closed by client") == true
    }

    fun send(text: String): Boolean {
        return runningSocket?.send(text) == true
    }

    fun send(bytes: ByteArray) = runningSocket?.send(ByteString.of(*bytes)) == true

    fun isConnected() = runningSocket != null

    override fun onOpen(webSocket: WebSocket, response: Response) {
        this.runningSocket = webSocket
        listener.onOpen(response.toString())
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        listener.onTextMessage(text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        listener.onClosing(code, reason)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        okHttpClient?.dispatcher()?.cancelAll()
        runningSocket = null
        listener.onClosed(code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        okHttpClient?.dispatcher()?.cancelAll()
        runningSocket = null
        val code = response?.code() ?: 520
        val failureResponsebody: String? = try {
            response?.body()?.string()
        } catch (e: IOException) {
            "Unknown (decode failed)"
        }
        listener.onFailure(t, code, failureResponsebody)
    }

    private fun connectOkHttpWebSocket(endpoint: String, uuid: String) {
        okHttpClient = SslOkHttpClientBuilderBase()
            .setup(context)
            .connectTimeout(CONNECT_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .pingInterval(PING_INTERVAL_MS, TimeUnit.MILLISECONDS)
            .build()
        okHttpClient?.newWebSocket(
            Request.Builder()
                .addHeader("uuid", uuid)
                .url(endpoint)
                .build(),
            this
        )
    }
}
