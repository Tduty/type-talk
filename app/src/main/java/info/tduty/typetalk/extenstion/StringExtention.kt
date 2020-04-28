package info.tduty.typetalk.extenstion

/**
 * Created by Evgeniy Mezentsev on 28.04.2020.
 */
const val RUSSIAN_SYMBOLS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"

fun String.hasRussianSymbols(): Boolean {
    for (char in RUSSIAN_SYMBOLS) {
        if (this.contains(char, true)) return true
    }
    return false
}