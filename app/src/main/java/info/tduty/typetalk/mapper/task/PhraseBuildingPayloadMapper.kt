package info.tduty.typetalk.mapper.task

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import info.tduty.typetalk.data.model.PhraseBuildingVO
import info.tduty.typetalk.mapper.TaskPayloadMapper


/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
class PhraseBuildingPayloadMapper  : TaskPayloadMapper {

    override fun map(payload: String): List<PhraseBuildingVO> {
        try {
            val tradeElement: JsonElement = JsonParser().parse(payload)
            val payloadJson = tradeElement.asJsonArray
            return payloadJson.mapNotNull { getPhraseBuildingVO(it) }
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
        } catch (ex: JsonSyntaxException) {
            ex.printStackTrace()
        }
        return emptyList()
    }

    private fun getPhraseBuildingVO(payloadElement: JsonElement): PhraseBuildingVO? {
        try {
            val id = payloadElement.asJsonObject.get("id").asString
            val phrases = payloadElement.asJsonObject.get("phrases").asJsonArray.map { it.asString }.toList()

            return PhraseBuildingVO(id, phrases)
        } catch (ex: JsonSyntaxException) {
            ex.printStackTrace()
        }
        return null
    }
}