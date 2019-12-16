package info.tduty.typetalk.data.model

import androidx.annotation.DrawableRes

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */
data class TaskVO(
    val id: String,
    val type: TaskType,
    @DrawableRes val icon: Int,
    val title: String,
    val optional: Boolean,
    val checked: Boolean
)