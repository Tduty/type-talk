package info.tduty.typetalk.data.pref

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
object UrlStorage {

    fun getUrl(): String {
        return "http://localhost:8080"
    }

    fun getWebsocketUrl(): String {
        return "wss://localhost:8080/ws"
    }
}
