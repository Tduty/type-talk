package info.tduty.typetalk.utils.view

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import info.tduty.typetalk.R
import java.util.concurrent.TimeUnit


open class TimerView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {
    private var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null
    private var mCircleOuterBounds: RectF? = null
    private var mCircleInnerBounds: RectF? = null
    private val mCirclePaint: Paint
    private val mEraserPaint: Paint
    private var mCircleSweepAngle = 0f
    private var mTimerAnimator: ValueAnimator? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    companion object {
        private const val ARC_START_ANGLE = 270f // 12 o'clock
        private const val THICKNESS_SCALE = 0.03f
    }

    init {
        var circleColor: Int = Color.RED
        if (attrs != null) {
            val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.TimerView)
            circleColor = ta.getColor(R.styleable.TimerView_circleColor, circleColor)
            ta.recycle()
        }
        mCirclePaint = Paint()
        mCirclePaint.isAntiAlias = true
        mCirclePaint.color = circleColor
        mEraserPaint = Paint()
        mEraserPaint.isAntiAlias = true
        mEraserPaint.color = Color.TRANSPARENT
        mEraserPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec) // Trick to make the view square
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (w != oldw || h != oldh) {
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            mBitmap?.eraseColor(Color.TRANSPARENT)
            mCanvas = Canvas(mBitmap!!)
        }
        super.onSizeChanged(w, h, oldw, oldh)
        updateBounds()
    }

    override fun onDraw(canvas: Canvas) {
        mCanvas?.drawColor(0, PorterDuff.Mode.CLEAR)
        if (mCircleSweepAngle > 0f) {
            mCanvas?.drawArc(
                mCircleOuterBounds!!,
                ARC_START_ANGLE,
                mCircleSweepAngle,
                true,
                mCirclePaint
            )
            mCanvas?.drawOval(mCircleInnerBounds!!, mEraserPaint)
        }
        canvas.drawBitmap(mBitmap!!, 0f, 0f, null)
    }

    fun start(secs: Long) {
        stop()
        mTimerAnimator = ValueAnimator.ofFloat(0f, 1f)
        mTimerAnimator?.duration = TimeUnit.SECONDS.toMillis(secs)
        mTimerAnimator?.interpolator = LinearInterpolator()
        mTimerAnimator?.addUpdateListener { animation ->
            drawProgress(
                animation.animatedValue as Float
            )
        }
        mTimerAnimator?.start()
    }

    fun stop() {
        if (mTimerAnimator != null && mTimerAnimator!!.isRunning) {
            mTimerAnimator!!.cancel()
            mTimerAnimator = null
            drawProgress(0f)
        }
    }

    private fun drawProgress(progress: Float) {
        mCircleSweepAngle = 360 * progress
        invalidate()
    }

    private fun updateBounds() {
        val thickness = width * THICKNESS_SCALE
        mCircleOuterBounds = RectF(0f, 0f, width.toFloat(), height.toFloat())
        mCircleInnerBounds = RectF(
            mCircleOuterBounds!!.left + thickness,
            mCircleOuterBounds!!.top + thickness,
            mCircleOuterBounds!!.right + thickness,
            mCircleOuterBounds!!.bottom + thickness
        )
        invalidate()
    }
}