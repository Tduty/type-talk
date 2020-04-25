package info.tduty.typetalk.mapper.task

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import info.tduty.typetalk.data.model.TaskPayloadVO
import info.tduty.typetalk.data.model.TranslationVO
import info.tduty.typetalk.mapper.TaskPayloadMapper

class TranslationPayloadMapper : TaskPayloadMapper {

    override fun map(payload: String): List<TaskPayloadVO> {
        try {
            val jsonElement: JsonElement = JsonParser().parse(payload)
            val payloadJson = jsonElement.asJsonArray
            return payloadJson.mapNotNull { getTranslationVO(it) }
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
        } catch (ex: JsonSyntaxException) {
            ex.printStackTrace()
        }
        return emptyList()
    }

    private fun getTranslationVO(payloadElement: JsonElement): TaskPayloadVO? {
        try {
            val type = payloadElement.asJsonObject.get("type").asString
            val word = payloadElement.asJsonObject.get("word").asString
            val currentTranslate = payloadElement.asJsonObject.get("current_translate").asString

            return TranslationVO(type, word, currentTranslate)
        } catch (ex: JsonSyntaxException) {
            ex.printStackTrace()
        }

        return null
    }
}