package info.tduty.typetalk.mapper.task

import com.google.gson.*
import info.tduty.typetalk.data.model.FlashcardVO
import info.tduty.typetalk.data.model.TaskPayloadVO
import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.mapper.TaskPayloadMapper
import java.util.*
import kotlin.collections.ArrayList


class FlashcardPayloadMapper : TaskPayloadMapper {

    override fun map(payload: String): List<TaskPayloadVO> {
        val parser = JsonParser()
        val tradeElement: JsonElement = parser.parse(payload)
        val payloadJson = tradeElement.asJsonArray

        val flashcardVOList = ArrayList<FlashcardVO>()
        for (payloadElement in payloadJson) {
            val flashcardVO = getFlashcardVO(payloadElement)
            flashcardVO.let {
                flashcardVOList.add(flashcardVO!!)
            }
        }
        return flashcardVOList
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