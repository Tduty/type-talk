package info.tduty.typetalk.utils

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
data class StringList(val strings: List<String> = emptyList())

fun List<String>.toStringList(): StringList {
    return StringList(this)
}