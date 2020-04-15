package info.tduty.typetalk.mapper.task

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import info.tduty.typetalk.data.model.DictionaryPictionaryVO
import info.tduty.typetalk.data.model.TaskPayloadVO
import info.tduty.typetalk.mapper.TaskPayloadMapper

class DictionaryPictionaryMapper : TaskPayloadMapper {

    override fun map(payload: String): List<TaskPayloadVO> {
        try {
            val tradeElement: JsonElement = JsonParser().parse(payload)
            val payloadJson = tradeElement.asJsonArray
            return payloadJson.mapNotNull { getDictionaryPictionaryVO(it) }
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
        } catch (ex: JsonSyntaxException) {
            ex.printStackTrace()
        }
        return emptyList()
    }

    private fun getDictionaryPictionaryVO(payloadElement: JsonElement): DictionaryPictionaryVO? {
        try {
            val url = payloadElement.asJsonObject.get("picture").asString
            val listTranslationJson = payloadElement.asJsonObject.get("translates").asJsonArray
            val listTranslation = ArrayList<String>()

            for (translationJson in listTranslationJson) {
                listTranslation.add(translationJson.asString)
            }

            return DictionaryPictionaryVO(url, listTranslation, "")
        } catch (ex: JsonSyntaxException) {
            ex.printStackTrace()
        }
        return null
    }
}