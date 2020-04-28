package info.tduty.typetalk.domain.mapper.task

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import info.tduty.typetalk.data.model.WordamessVO
import info.tduty.typetalk.domain.mapper.TaskPayloadMapper

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
class WordamessPayloadMapper : TaskPayloadMapper {

    override fun map(payload: String): List<WordamessVO> {
        try {
            val tradeElement: JsonElement = JsonParser().parse(payload)
            val payloadJson = tradeElement.asJsonArray
            return payloadJson.mapNotNull { getWordamessVO(it) }
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
        } catch (ex: JsonSyntaxException) {
            ex.printStackTrace()
        }
        return emptyList()
    }

    private fun getWordamessVO(payloadElement: JsonElement): WordamessVO? {
        try {
            val isMistake = payloadElement.asJsonObject.get("mistake").asBoolean
            val body = payloadElement.asJsonObject.get("body").asString
            val correctBody = payloadElement.asJsonObject.get("correct_body")?.asString

            return WordamessVO(isMistake, body, correctBody)
        } catch (ex: JsonSyntaxException) {
            ex.printStackTrace()
        }
        return null
    }
}