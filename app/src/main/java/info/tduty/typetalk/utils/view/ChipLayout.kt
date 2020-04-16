package info.tduty.typetalk.utils.view

import android.content.ClipData
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import info.tduty.typetalk.R
import kotlinx.android.synthetic.main.item_chip.view.*


/**
 * Created by Evgeniy Mezentsev on 11.04.2020.
 */
class ChipLayout(context: Context, attrs: AttributeSet) : FlowLayout(context, attrs) {

    private val selectedView = mutableSetOf<View>()

    private val selectedColor: Int
    private val unselectedColor: Int
    private val isClickableChip: Boolean
    private val isDragAndDropChip: Boolean

    init {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.FlowLayout, 0, 0)
        selectedColor = a.getColor(R.styleable.FlowLayout_selectedColor, -1)
        unselectedColor = a.getColor(R.styleable.FlowLayout_deselectedColor, -1)
        isClickableChip = a.getBoolean(R.styleable.FlowLayout_isClickableChip, false)
        isDragAndDropChip = a.getBoolean(R.styleable.FlowLayout_isDragAndDropChip, false)
    }

    fun addChip(view: View) {
        addView(view)
    }

    fun addChip(text: String, clickListener: ((Boolean, String) -> Unit)? = null) {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chip, this, false)
        view.tv_chip.text = text
        if (isClickableChip) setClickableListener(view, text, clickListener)
        if (isDragAndDropChip) setDragAndDropChip(view)
        addView(view)
    }

    fun clearAll() {
        removeAllViews()
    }

    private fun setClickableListener(
        view: View, text: String,
        clickListener: ((Boolean, String) -> Unit)? = null
    ) {
        view.cv_chip.setOnClickListener {
            if (selectedView.contains(view)) {
                selectedView.remove(view)
                updateUnselectedColor(view)
                clickListener?.invoke(false, text)
            } else {
                selectedView.add(view)
                updateSelectedColor(view)
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

    private fun updateSelectedColor(view: View) {
        if (selectedColor != -1) view.cv_chip.setCardBackgroundColor(selectedColor)
    }

    private fun updateUnselectedColor(view: View) {
        if (unselectedColor != -1) view.cv_chip.setCardBackgroundColor(unselectedColor)
    }
}
