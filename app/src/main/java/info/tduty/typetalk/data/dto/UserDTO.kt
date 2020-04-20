package info.tduty.typetalk.data.dto

import com.google.gson.annotations.SerializedName


/**
 * Created by Evgeniy Mezentsev on 05.04.2020.
 */
data class UserDTO(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("class_name") val className: String?,
    @SerializedName("teacher") val isTeacher: Boolean
)
