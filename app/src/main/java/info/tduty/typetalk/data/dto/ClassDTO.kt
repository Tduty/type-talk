package info.tduty.typetalk.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
data class ClassDTO(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("member_count")
    val memberCount: Int
)
