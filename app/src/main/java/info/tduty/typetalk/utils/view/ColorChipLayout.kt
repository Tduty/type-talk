package info.tduty.typetalk.utils.view

import android.content.ClipData
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.annotation.LayoutRes
import info.tduty.typetalk.R
import kotlinx.android.synthetic.main.item_chip.view.*

class ColorChipLayout(context: Context, attrs: AttributeSet) : FlowLayout(context, attrs) {

    private val DEFAULT_COLOR: Int = R.color.white
    private val selectedView = mutableSetOf<View>()

    private val isClickableChip: Boolean
    private val isDragAndDropChip: Boolean

    var singleClick = false

    enum class TypeColor {
        SELECTED,
        BACKGROUND,
        UNSELECETED
    }

    init {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.FlowLayout, 0, 0)
        isClickableChip = a.getBoolean(R.styleable.FlowLayout_isClickableChip, false)
        isDragAndDropChip = a.getBoolean(R.styleable.FlowLayout_isDragAndDropChip, false)
    }

    fun addChip(text: String, colorMap: Map<TypeColor, Int>, @LayoutRes idChipLayout: Int = R.layout.item_chip, clickListener: ((Boolean, String) -> Unit)? = null) {
        val view = LayoutInflater.from(context).inflate(idChipLayout, this, false)
        view.tv_chip.text = text
        if (isClickableChip) setClickableListener(view, text, colorMap, clickListener)
        if (isDragAndDropChip) setDragAndDropChip(view)
        addView(view)
    }

    fun clearAll() {
        removeAllViews()
    }

    private fun setClickableListener(
        view: View,
        text: String,
        colorMap: Map<TypeColor, Int>?,
        clickListener: ((Boolean, String) -> Unit)? = null
    ) {
        view.cv_chip.setOnClickListener {
            if (singleClick) {
                view.cv_chip.isClickable = false
            }
            if (selectedView.contains(view)) {
                selectedView.remove(view)
                colorMap?.let {
                    updateUnselectedColor(view, it[TypeColor.UNSELECETED] ?: DEFAULT_COLOR)
                }
                clickListener?.invoke(false, text)
            } else {
                selectedView.add(view)
                colorMap?.let {
                    updateSelectedColor(view, it[TypeColor.SELECTED] ?: DEFAULT_COLOR)
                }
                clickListener?.invoke(true, text)
            }
        }
    }

    private fun setDragAndDropChip(moveView: View) {
        moveView.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = DragShadowBuilder(view)
                view.startDrag(data, shadowBuilder, view, 0)
                view.visibility = View.INVISIBLE
                true
            } else {
                false
            }
        }
    }

    private fun updateSelectedColor(view: View, color: Int) {
        val resourcesColor = resources.getColor(color)
        if (color != -1) view.cv_chip.setCardBackgroundColor(resourcesColor)
    }

    private fun updateUnselectedColor(view: View, color: Int) {
        val resourcesColor = resources.getColor(color)
        if (color != -1) view.cv_chip.setCardBackgroundColor(resourcesColor)
    }
}