package info.tduty.typetalk.data.event

import java.util.*

/**
 * Created by Evgeniy Mezentsev on 04.03.2020.
 */
interface EventPayload {

    enum class Type {
        MESSAGE_NEW, LESSON, TYPING;

        val string: String
            get() = name.toLowerCase(Locale.ROOT)

        companion object {
            fun String.toType(): Type {
                return valueOf(this.toUpperCase(Locale.ROOT))
            }

            fun to(type: String): Type {
                return valueOf(type.toUpperCase(Locale.ROOT))
            }
        }
    }
}

