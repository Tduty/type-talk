package info.tduty.typetalk.utils

object Utils {

    fun getSuccessCompletedTaskPercent(countTask: Int, countSuccessTask: Int) : Double {
        if (countTask <= 0) return 0.0
        return kotlin.math.ceil((countSuccessTask / countTask) * 100.0)
    }
}