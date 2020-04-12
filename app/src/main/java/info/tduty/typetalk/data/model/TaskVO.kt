package info.tduty.typetalk.data.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */
@Parcelize
data class TaskVO(
    val id: String,
    val type: TaskType,
    @DrawableRes val icon: Int = 0,
    val title: String,
    val payload: String,
    val optional: Boolean = false,
    val checked: Boolean = false
) : Parcelable