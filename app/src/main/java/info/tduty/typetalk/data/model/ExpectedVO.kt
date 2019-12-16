package info.tduty.typetalk.data.model

import androidx.annotation.DrawableRes

/**
 * Created by Evgeniy Mezentsev on 2019-11-20.
 */
data class ExpectedVO(
    val title: String,
    @DrawableRes val icon: Int
)