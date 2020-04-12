package info.tduty.typetalk.data.pref

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
object UrlStorage {

    fun getUrl(): String {
        return "http://192.168.0.105:8080"
    }

    fun getWebsocketUrl(): String {
        return "ws://192.168.0.105:8080/ws"
    }
}
