package info.tduty.typetalk.socket

import info.tduty.typetalk.data.event.EventPayload
import info.tduty.typetalk.data.event.payload.*
import io.reactivex.Flowable

/**
 * Created by Evgeniy Mezentsev on 04.03.2020.
 */
class SocketEventListener(private val eventBus: EventBusRx) {

    fun listenEvents(client: SocketClient) {
        client.listenEvent(EventPayload.Type.LESSON, eventBus)
        client.listenEvent(EventPayload.Type.MESSAGE_NEW, eventBus)
        client.listenEvent(EventPayload.Type.TYPING, eventBus)
        client.listenEvent(EventPayload.Type.USER_STATUS, eventBus)
        client.listenEvent(EventPayload.Type.CORRECTION, eventBus)
        client.listenEvent(EventPayload.Type.TASK, eventBus)
        client.listenEvent(EventPayload.Type.LESSON_PROGRESS, eventBus)
    }

    fun messageNewPayloadObservable(): Flowable<MessageNewPayload> {
        return eventBus
            .observeEvents(MessageNewPayload::class.java)
    }

    fun userStatusPayloadObservable(): Flowable<UserStatusPayload> {
        return eventBus
            .observeEvents(UserStatusPayload::class.java)
    }

    fun typingPayloadObservable(): Flowable<TypingPayload> {
        return eventBus
            .observeEvents(TypingPayload::class.java)
    }

    fun lessonPayloadObservable(): Flowable<LessonPayload> {
        return eventBus
            .observeEvents(LessonPayload::class.java)
    }

    fun correctionPayloadObservable(): Flowable<CorrectionPayload> {
        return eventBus
            .observeEvents(CorrectionPayload::class.java)
    }

    fun completedTaskPayloadObservable(): Flowable<CompleteTaskPayload> {
        return eventBus
            .observeEvents(CompleteTaskPayload::class.java)
    }

    fun lessonProgressPayloadObservable(): Flowable<LessonProgressPayload> {
        return eventBus
            .observeEvents(LessonProgressPayload::class.java)
    }
}
