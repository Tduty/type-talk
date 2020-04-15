package info.tduty.typetalk.data.model

class DictionaryPictionaryVO(
  val url: String,
  val translates: List<String>,
  var inputWord: String?
) : TaskPayloadVO(TaskType.DICTIONARY_PICTIONARY)