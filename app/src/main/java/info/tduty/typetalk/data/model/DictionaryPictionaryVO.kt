package info.tduty.typetalk.data.model

import android.graphics.Bitmap
import android.graphics.drawable.Drawable

class DictionaryPictionaryVO(
  val url: String,
  val translates: List<String>,
  var inputWord: String?,
  var image: Drawable? = null
) : TaskPayloadVO(TaskType.DICTIONARY_PICTIONARY) {

  fun isSuccess() : Boolean {
    return translates.contains(inputWord)
  }
}