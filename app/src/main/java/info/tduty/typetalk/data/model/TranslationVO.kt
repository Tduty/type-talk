package info.tduty.typetalk.data.model

class TranslationVO(
    val type: String,
    val word: String,
    val currentTranslation: String,
    var inputWord: String? = ""
) : TaskPayloadVO(TaskType.TRANSLATION) {

    companion object {
        val PHRASE_TYPE = "phrase"
        val SENTENCE_TYPE = "sentence"
    }

    fun isValid() : Boolean {
        return currentTranslation == inputWord
    }
}