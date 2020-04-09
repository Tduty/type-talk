package info.tduty.typetalk.socket

import info.tduty.typetalk.data.event.EventPayload
import info.tduty.typetalk.data.event.payload.LessonPayload
import info.tduty.typetalk.data.event.payload.MessageNewPayload
import info.tduty.typetalk.data.event.payload.TypingPayload
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 04.03.2020.
 */
class SocketEventListener(private val eventBus: EventBusRx) {

    fun listenEvents(client: SocketClient) {
        client.listenEvent(EventPayload.Type.LESSON, eventBus)
        client.listenEvent(EventPayload.Type.MESSAGE_NEW, eventBus)
        client.listenEvent(EventPayload.Type.TYPING, eventBus)
    }

    fun messageNewPayloadObservable(): Flowable<MessageNewPayload> {
        return eventBus
            .observeEvents(MessageNewPayload::class.java)
    }

    fun typingPayloadObservable(): Flowable<TypingPayload> {
        return eventBus
            .observeEvents(TypingPayload::class.java)
    }

    fun lessonPayloadObservable(): Flowable<LessonPayload> {
        return eventBus
            .observeEvents(LessonPayload::class.java)
    }
}
