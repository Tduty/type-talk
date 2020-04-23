package info.tduty.typetalk.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
@Parcelize
data class DialogVO(
    val chatId: String,
    val title: String
): Parcelable
