package info.tduty.typetalk.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Evgeniy Mezentsev on 09.04.2020.
 */
data class DictionaryDTO(
    @SerializedName("id")
    val id: String,

    @SerializedName("lesson_id")
    val lessonId: String,

    @SerializedName("lesson_name")
    val lessonName: String,

    @SerializedName("phrase")
    val phrase: String,

    @SerializedName("translation")
    val translation: String,

    @SerializedName("transcription")
    val transcription: String
)
