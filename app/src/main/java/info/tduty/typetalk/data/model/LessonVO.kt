package info.tduty.typetalk.data.model

import androidx.annotation.DrawableRes
import info.tduty.typetalk.R

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
) {
    companion object {

        fun getStatus(state: Int) : StatusVO {
            return when(state) {
                0 -> StatusVO(R.drawable.ic_checkbox_empty, "Awaible")
                1 -> StatusVO(R.drawable.ic_checkbox_empty, "Progress")
                2 -> StatusVO(R.drawable.ic_checkbox_complete, "Completed")
                else -> StatusVO(R.drawable.ic_checkbox_empty, "Awaible")
            }
        }
    }
}