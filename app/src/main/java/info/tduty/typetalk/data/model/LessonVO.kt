package info.tduty.typetalk.data.model

import androidx.annotation.DrawableRes

/**
 * Created by Evgeniy Mezentsev on 2019-11-20.
 */
data class LessonVO(
    val id: String,
    val number: Int,
    val title: String,
    @DrawableRes val icon: Int,
    val status: StatusVO,
    val content: String,
    val expectedList: List<ExpectedVO>
)