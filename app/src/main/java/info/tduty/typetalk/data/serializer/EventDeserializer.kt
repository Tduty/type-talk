package info.tduty.typetalk.data.serializer

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import info.tduty.typetalk.data.event.Event
import info.tduty.typetalk.data.event.EventPayload
import info.tduty.typetalk.data.event.EventPayload.Type.*
import info.tduty.typetalk.data.event.payload.LessonPayload
import info.tduty.typetalk.data.event.payload.MessageNewPayload
import info.tduty.typetalk.data.event.payload.TypingPayload
import java.lang.reflect.Type

/**
 * Created by Evgeniy Mezentsev on 04.03.2020.
 */
class EventDeserializer(val gson: Gson) : JsonDeserializer<Event> {

    override fun deserialize(
        json: JsonElement,
        type: Type?,
        jsonDeserializationContext: JsonDeserializationContext?
    ): Event {
        val event = gson.fromJson<Event>(json, Event::class.java)
        val jsonElement = json.asJsonObject["payload"]
        event.eventPayload = when (EventPayload.Type.to(event.type)) {
            MESSAGE_NEW -> gson.fromJson(jsonElement, MessageNewPayload::class.java)
            LESSON -> gson.fromJson(jsonElement, LessonPayload::class.java)
            TYPING -> gson.fromJson(jsonElement, TypingPayload::class.java)
        }
        return event
    }
}
