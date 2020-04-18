package info.tduty.typetalk.utils.timer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import info.tduty.typetalk.R


class Circle(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint: Paint
    private val rect: RectF

    private val fillRect: RectF

    var angle: Float
    var startAngle: Float

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimerCircle)
        startAngle = typedArray.getFloat(R.styleable.TimerCircle_startAngle, 0f)
        val offsetAngle = typedArray.getFloat(R.styleable.TimerCircle_offsetAngle, 0f)
        val color = typedArray.getColor(R.styleable.TimerCircle_color, 0)
        val strokeWidth = typedArray.getDimension(R.styleable.TimerCircle_strokeWidth, 20f)
        val circleSize = typedArray.getDimension(R.styleable.TimerCircle_cicleSize, 100f)
        typedArray.recycle()

        paint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            setStrokeWidth(strokeWidth)
            setColor(color)
        }

        rect = RectF(
            strokeWidth,
            strokeWidth,
            (circleSize - strokeWidth),
            (circleSize - strokeWidth)
        )

        val offsetFill = strokeWidth
        fillRect = RectF(
            offsetFill,
            offsetFill,
            (circleSize - offsetFill),
            (circleSize - offsetFill)
        )

        this.layoutParams
        //Initial Angle (optional, it can be zero)
        angle = offsetAngle
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(rect, startAngle, angle, false, paint)
    }
}