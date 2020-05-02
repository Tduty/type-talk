package info.tduty.typetalk.utils.timer

import android.os.CountDownTimer
import android.view.animation.Animation
import android.view.animation.Transformation
import kotlin.math.abs
import kotlin.math.ceil

class CircleAngleAnimation(
    circle: Circle,
    newAngle: Int,
    val millisInFuture: Long,
    val countDownInterval: Long,
    val onChangeValue: ((value: Long) -> Unit)? = null,
    val onFinished: (() -> Unit)? = null
) : Animation(), Animation.AnimationListener {

    private var lastInterpolatedTime: Float = 0.0f
    private var timer: CountDownTimer? = null
    private val circle: Circle
    private var oldAngle: Float
    private var newAngle: Float

    private var isSaved = false
    private var millisInFutureSaved = 0L

    private var penatlyAnimPercent: Double = 0.0
    private var penatlyTimerPercent: Double = 0.0

    init {
        duration = millisInFuture
        oldAngle = circle.angle
        this.newAngle = newAngle.toFloat()
        this.circle = circle
    }

    override fun applyTransformation(
        interpolatedTime: Float,
        transformation: Transformation?
    ) {
        this.lastInterpolatedTime = interpolatedTime
        val angle = oldAngle + (newAngle - oldAngle) * lastInterpolatedTime
        circle.angle = angle
        circle.requestLayout()
    }

    override fun onAnimationRepeat(animation: Animation?) {}

    override fun onAnimationEnd(animation: Animation?) {}

    override fun onAnimationStart(animation: Animation?) {
        var sec = millisInFuture
        if (isSaved) {
            sec = millisInFutureSaved
            duration = millisInFutureSaved
        } else {
            oldAngle = 360f
        }
        timer = object: CountDownTimer(sec, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                millisInFutureSaved = millisUntilFinished
                onChangeValue?.invoke(millisInFutureSaved)
            }

            override fun onFinish() {
                isSaved = false
                circle.angle = 0f
                circle.requestLayout()
                onChangeValue?.invoke(0)
                onFinished?.invoke()
            }
        }
        timer?.start()
    }

    override fun start() {
        penatlyAnimPercent = 0.0
        penatlyTimerPercent = 0.0
        super.start()
    }

    fun stop() {
        isSaved = true
        oldAngle = circle.angle
        this.cancel()
        timer?.cancel()
        circle.requestLayout()
    }

    fun setPenatly(second: Int) {
        penatlyAnimPercent = getPenatlyPercent(second)
        penatlyTimerPercent = getPenatlyPercent(second)
        stop()
        millisInFutureSaved = abs(millisInFutureSaved - (millisInFuture.toDouble() * (penatlyAnimPercent / 100.0))).toLong()
        oldAngle -= (abs(newAngle - 360) * (penatlyAnimPercent / 100.0)).toFloat()

        var duration = duration
        duration -= (second * countDownInterval)

        if (oldAngle <= 0 || duration <= 0) {
            timer?.onFinish()
            return
        } else {
            this.duration = duration
        }

        start()
    }

    private fun getPenatlyPercent(second: Int): Double {
        if (second <= 0) return 0.0
        val allSecond = millisInFuture.toDouble() / countDownInterval.toDouble()
        return ceil((second / allSecond) * 100.0)
    }
}