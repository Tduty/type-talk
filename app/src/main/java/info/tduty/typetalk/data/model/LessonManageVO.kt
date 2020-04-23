package info.tduty.typetalk.data.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
@Parcelize
data class LessonManageVO(
    val id: String,
    val number: Int,
    val name: String,
    @DrawableRes val icon: Int,
    val studentsCount: Int,
    val completedCount: Int = 0,
    val isLockForClass: Boolean = false
) : Parcelable
