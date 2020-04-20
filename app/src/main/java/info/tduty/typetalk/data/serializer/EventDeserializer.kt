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
import info.tduty.typetalk.data.event.payload.UserStatusPayload
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
        val typePayload = json.asJsonObject["type"].asString
        val jsonElement = json.asJsonObject["eventPayload"]
        val eventPayload = when (EventPayload.Type.to(typePayload)) {
            USER_STATUS -> gson.fromJson(jsonElement, UserStatusPayload::class.java)
            MESSAGE_NEW -> gson.fromJson(jsonElement, MessageNewPayload::class.java)
            LESSON -> gson.fromJson(jsonElement, LessonPayload::class.java)
            TYPING -> gson.fromJson(jsonElement, TypingPayload::class.java)
        }
        return Event(typePayload, eventPayload)
    }
}
