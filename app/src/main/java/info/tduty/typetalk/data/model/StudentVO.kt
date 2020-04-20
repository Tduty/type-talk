package info.tduty.typetalk.data.model

import androidx.annotation.DrawableRes
import info.tduty.typetalk.R
import info.tduty.typetalk.data.db.model.StudentEntity

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
data class StudentVO(
    val id: String,
    val chatId: String,
    val name: String,
    val type: Type,
    var status: Status,
    var action: String? = null,
    var existNewMessages: Boolean = false
) {

    enum class Status {
        NOT_CONNECTED,
        DISCONNECTED,
        CONNECTED;

        companion object {
            fun entityOf(status: String): Status {
                return when (status) {
                    StudentEntity.CONNECTED_STATUS -> CONNECTED
                    StudentEntity.DISCONNECTED_STATUS -> DISCONNECTED
                    else -> NOT_CONNECTED
                }
            }
        }
    }

    enum class Type(@DrawableRes val icon: Int) {
        MALE(R.drawable.ic_boy_bg_varden),
        FEMALE(R.drawable.ic_girl_bg_varden);

        companion object {
            fun entityOf(type: String): Type {
                return when (type) {
                    StudentEntity.FEMALE_TYPE -> FEMALE
                    else -> MALE
                }
            }
        }
    }
}
