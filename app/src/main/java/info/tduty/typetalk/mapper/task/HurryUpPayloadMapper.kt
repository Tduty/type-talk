package info.tduty.typetalk.mapper.task

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import info.tduty.typetalk.data.model.HurryUpVO
import info.tduty.typetalk.mapper.TaskPayloadMapper

class HurryUpPayloadMapper : TaskPayloadMapper {

    override fun map(payload: String): List<HurryUpVO> {
        try {
            val tradeElement: JsonElement = JsonParser().parse(payload)
            val payloadJson = tradeElement.asJsonArray
            return payloadJson.mapNotNull { getHurryUpVO(it) }
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
        } catch (ex: JsonSyntaxException) {
            ex.printStackTrace()
        }
        return emptyList()
    }

    private fun getHurryUpVO(payloadElement: JsonElement): HurryUpVO? {
        try {
            val word = payloadElement.asJsonObject.get("word").asString
            val type = payloadElement.asJsonObject.get("type").asString
            val translate = payloadElement.asJsonObject.get("translate").asString
            val anyTranslates = payloadElement.asJsonObject.get("any_translates").asJsonArray.map { it.asString }.toList()

            return HurryUpVO(word, type, translate, false, anyTranslates)
        } catch (ex: JsonSyntaxException) {
            ex.printStackTrace()
        }
        return null
    }
}