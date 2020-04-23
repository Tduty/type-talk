package info.tduty.typetalk.domain.mapper.task

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import info.tduty.typetalk.data.model.FlashcardVO
import info.tduty.typetalk.data.model.TaskPayloadVO
import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.domain.mapper.TaskPayloadMapper

class FlashcardPayloadMapper : TaskPayloadMapper {

    override fun map(payload: String): List<TaskPayloadVO> {
        try {
            val tradeElement: JsonElement = JsonParser().parse(payload)
            val payloadJson = tradeElement.asJsonArray
            return payloadJson.mapNotNull { getFlashcardVO(it) }
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
        } catch (ex: JsonSyntaxException) {
            ex.printStackTrace()
        }
        return emptyList()
    }

    private fun getFlashcardVO(payloadElement: JsonElement): FlashcardVO? {
        try {
            val type = payloadElement.asJsonObject.get("type").asString
            val frontWord = payloadElement.asJsonObject.get("front").asString
            val backWord = payloadElement.asJsonObject.get("back").asString

            return FlashcardVO(type, frontWord, backWord, TaskType.FLASHCARDS)
        } catch (ex: JsonSyntaxException) {
            ex.printStackTrace()
        }
        return null
    }
}