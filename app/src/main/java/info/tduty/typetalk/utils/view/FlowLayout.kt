package info.tduty.typetalk.utils.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import info.tduty.typetalk.R
import java.util.*

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
open class FlowLayout(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    var lineHeight = 0
    private val layoutProcessor = LayoutProcessor()
    var verticalSpacing = 0
    var minHorizontalSpacing = 0
    val gravity: Gravity =
        Gravity.STAGGERED

    enum class Gravity {
        LEFT, RIGHT, CENTER, STAGGERED
    }

    init {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.FlowLayout, 0, 0)
        minHorizontalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_minHorizontalSpacing, resources.getDimensionPixelSize(R.dimen.flow_min_horizontal_spacing))
        verticalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_verticalSpacing, resources.getDimensionPixelSize(R.dimen.flow_vertical_spacing))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        assert(MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED)
        val width = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight
        var height = MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom
        val count: Int = childCount
        var lineHeight = 0
        var xPos: Int = paddingLeft
        var yPos: Int = paddingTop
        val childHeightMeasureSpec: Int
        childHeightMeasureSpec =
            if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST)
            } else {
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            }
        for (i in 0 until count) {
            val child: View = getChildAt(i)
            if (child.visibility != View.GONE) {
                child.measure(
                    MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
                    childHeightMeasureSpec
                )
                val childW = child.measuredWidth
                lineHeight = Math.max(lineHeight, child.measuredHeight + verticalSpacing)
                if (xPos + childW > width) {
                    xPos = paddingLeft
                    yPos += lineHeight
                }
                xPos += childW + getMinimumHorizontalSpacing()
            }
        }
        this.lineHeight = lineHeight
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED || MeasureSpec.getMode(
                heightMeasureSpec
            ) == MeasureSpec.AT_MOST && yPos + lineHeight < height
        ) {
            height = yPos + lineHeight
        }
        setMeasuredDimension(width, height)
    }

    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        val count: Int = childCount
        val width = r - l
        var xPos: Int = paddingLeft
        var yPos: Int = paddingTop
        layoutProcessor.setWidth(width)
        for (i in 0 until count) {
            val child: View = getChildAt(i)
            if (child.visibility != View.GONE) {
                val childW = child.measuredWidth
                val childH = child.measuredHeight
                if (xPos + childW > width) {
                    xPos = paddingLeft
                    yPos += lineHeight
                    layoutProcessor.layoutPreviousRow()
                }
                layoutProcessor.addViewForLayout(child, yPos, childW, childH)
                xPos += childW + getMinimumHorizontalSpacing()
            }
        }
        layoutProcessor.layoutPreviousRow()
        layoutProcessor.clear()
    }

    fun getMinimumHorizontalSpacing(): Int {
        return minHorizontalSpacing
    }

    fun getCurrentGravity() = gravity

    inner class LayoutProcessor() {

        private var rowY = 0
        private val viewsInCurrentRow: MutableList<View>
        private val viewWidths: MutableList<Int>
        private val viewHeights: MutableList<Int>
        private var width = 0

        fun addViewForLayout(
            view: View,
            yPos: Int,
            childW: Int,
            childH: Int
        ) {
            rowY = yPos
            viewsInCurrentRow.add(view)
            viewWidths.add(childW)
            viewHeights.add(childH)
        }

        fun clear() {
            viewsInCurrentRow.clear()
            viewWidths.clear()
            viewHeights.clear()
        }

        fun layoutPreviousRow() {
            val gravity = getCurrentGravity()
            val minimumHorizontalSpacing = getMinimumHorizontalSpacing()
            when (gravity) {
                Gravity.LEFT -> {
                    var xPos: Int = paddingLeft
                    var i = 0
                    while (i < viewsInCurrentRow.size) {
                        viewsInCurrentRow[i]
                            .layout(xPos, rowY, xPos + viewWidths[i], rowY + viewHeights[i])
                        xPos += viewWidths[i] + minimumHorizontalSpacing
                        i++
                    }
                }
                Gravity.RIGHT -> {
                    var xEnd: Int = width - getPaddingRight()
                    var i = viewsInCurrentRow.size - 1
                    while (i >= 0) {
                        val xStart = xEnd - viewWidths[i]
                        viewsInCurrentRow[i]
                            .layout(xStart, rowY, xEnd, rowY + viewHeights[i])
                        xEnd = xStart - minimumHorizontalSpacing
                        i--
                    }
                }
                Gravity.STAGGERED -> {
                    var totalWidthOfChildren = 0
                    run {
                        var i = 0
                        while (i < viewWidths.size) {
                            totalWidthOfChildren += viewWidths[i]
                            i++
                        }
                    }
                    val horizontalSpacingForStaggered: Int =
                        (width - totalWidthOfChildren - paddingLeft - paddingRight) /
                                (viewsInCurrentRow.size + 1)
                    var xPos = paddingLeft + horizontalSpacingForStaggered
                    var i = 0
                    while (i < viewsInCurrentRow.size) {
                        viewsInCurrentRow[i].layout(xPos, rowY, xPos + viewWidths[i], rowY + viewHeights[i])
                        xPos += viewWidths[i] + horizontalSpacingForStaggered
                        i++
                    }
                }
                Gravity.CENTER -> {
                    var totalWidthOfChildren = 0
                    run {
                        var i = 0
                        while (i < viewWidths.size) {
                            totalWidthOfChildren += viewWidths[i]
                            i++
                        }
                    }
                    var xPos = paddingLeft + (width - paddingLeft - paddingRight -
                            totalWidthOfChildren - minimumHorizontalSpacing * (viewsInCurrentRow.size - 1)) / 2
                    var i = 0
                    while (i < viewsInCurrentRow.size) {
                        viewsInCurrentRow[i]
                            .layout(xPos, rowY, xPos + viewWidths[i], rowY + viewHeights[i])
                        xPos += viewWidths[i] + minimumHorizontalSpacing
                        i++
                    }
                }
            }
            clear()
        }

        fun setWidth(width: Int) {
            this.width = width
        }

        init {
            viewsInCurrentRow = ArrayList()
            viewWidths = ArrayList()
            viewHeights = ArrayList()
        }
    }
}