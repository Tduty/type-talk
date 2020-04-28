package info.tduty.typetalk.data.model

import androidx.annotation.DrawableRes

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
data class TaskManageVO(
    val id: String,
    val lessonId: String,
    val title: String,
    val type: TaskType,
    @DrawableRes val icon: Int,
    val studentsCount: Int,
    val completedCount: Int = 0
)
