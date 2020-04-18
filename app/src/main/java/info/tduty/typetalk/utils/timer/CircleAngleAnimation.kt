package info.tduty.typetalk.utils.timer

import android.animation.Animator
import android.animation.TimeInterpolator
import android.os.Build
import android.os.CountDownTimer
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.core.view.ViewCompat.animate


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
        val angle = oldAngle + (newAngle - oldAngle) * interpolatedTime
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
                onChangeValue?.invoke(millisUntilFinished)
            }

            override fun onFinish() {
                onFinished?.invoke()
                isSaved = false
            }
        }
        timer?.start()
    }

    fun stop() {
        isSaved = true
        oldAngle = circle.angle
        this.cancel()
        timer?.cancel()
        circle.requestLayout()
    }
}