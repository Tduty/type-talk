package info.tduty.typetalk.utils.alert

import android.graphics.Bitmap
import android.graphics.drawable.Drawable

class AlertDialogItemsVO(
    val topWord: String,
    val bottomWord: String,
    val type: TypeAlertItem,
    val image: Drawable? = null
)

enum class TypeAlertItem {
    ERROR,
    INFO,
    IMAGE
}
