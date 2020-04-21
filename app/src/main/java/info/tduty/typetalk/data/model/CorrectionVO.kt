package info.tduty.typetalk.data.model

/**
 * Created by Evgeniy Mezentsev on 21.04.2020.
 */
data class CorrectionVO(
    val syncId: String,
    val chatId: String?,
    val additional: String,
    val type: AdditionalType
) {

    enum class AdditionalType(val id: Int) {

        CORRECTION(1), COMMENT(2), NONE(0);

        companion object {

            fun intOf(type: Int): AdditionalType {
                return when (type) {
                    1 -> CORRECTION
                    2 -> COMMENT
                    else -> NONE
                }
            }
        }
    }
}
