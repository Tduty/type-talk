package info.tduty.typetalk.view.task.phrasebuilding

import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.PhraseBuildingVO
import kotlinx.android.synthetic.main.item_pager_task_phrase_building.view.*

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
class PagerVH(
    itemView: View,
    private val presenter: PhraseBuilderItemPresenter
) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun newInstance(parent: ViewGroup, presenter: PhraseBuilderItemPresenter): PagerVH {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_pager_task_phrase_building, parent, false)
            return PagerVH(view, presenter)
        }
    }

    lateinit var phrases: PhraseBuildingVO
    var isLastVH = false

    fun onBind(phrases: PhraseBuildingVO, isLastVH: Boolean) {
        this.phrases = phrases
        this.isLastVH = isLastVH
        updateChips(phrases)
        updateText(phrases.buildingText, phrases.isCorrectText)
        if (phrases.isCorrectText) showCorrectState()
        else hideCorrectState()
        setupListeners()
        itemView.rl_correct_sentence.setOnDragListener { v, event ->
            when (event.action) {
                DragEvent.ACTION_DROP -> {
                    val chipView = event.localState as View
                    val owner = chipView.parent as ViewGroup
                    owner.removeView(chipView)
                    presenter.addText(phrases.id, chipView.tv_chip.text.toString())
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    val chipView = event.localState as View
                    chipView.visibility = View.VISIBLE
                }
                else -> {
                }
            }
            true
        }
    }

    fun updateText(text: String, isCorrectText: Boolean) {
        if (text.isBlank()) showTextPlaceholder()
        else {
            showTextField()
            itemView.tv_text.text = text
            if (isCorrectText) showCorrectBtn()
            else showCancelBtn()
        }
    }

    fun showSkip() {
        itemView.tv_skip.visibility = View.VISIBLE
    }

    fun hiddenSkip() {
        itemView.tv_skip.visibility = View.GONE
    }

    fun clearText() {
        itemView.tv_text.text = null
        showTextPlaceholder()
        updateChips(phrases)
    }

    fun showCorrectState() {
        if (isLastVH) itemView.btn_next.setText(R.string.button_complete)
        else itemView.btn_next.setText(R.string.button_next)
        itemView.btn_next.visibility = View.VISIBLE
        itemView.tv_info_about_task.visibility = View.VISIBLE
        itemView.tv_info_about_task.setText(R.string.task_screen_phrase_building_congratulation_description)
        itemView.fl_chip.visibility = View.GONE
    }

    fun showMessageAboutWrongOffer() {
        Toast.makeText(itemView.context, R.string.task_screen_phrase_building_wrong_sentence, Toast.LENGTH_SHORT).show()
    }

    private fun hideCorrectState() {
        itemView.fl_chip.visibility = View.VISIBLE
        itemView.tv_info_about_task.visibility = View.VISIBLE
        itemView.tv_info_about_task.setText(R.string.task_screen_phrase_building_description)
        itemView.btn_next.visibility = View.GONE
    }

    private fun setupListeners() {
        itemView.btn_next.setOnClickListener { presenter.nextPage(phrases.id) }
        itemView.iv_cancel.setOnClickListener { presenter.clearText(phrases.id) }
        itemView.tv_skip.setOnClickListener { presenter.nextPage(phrases.id) }
    }

    private fun updateChips(phrases: PhraseBuildingVO) {
        itemView.fl_chip.removeAllViews()
        phrases.phrases.shuffled().forEach { itemView.fl_chip.addChip(it) }
    }

    private fun showTextPlaceholder() {
        itemView.ll_drag_field_placeholder.visibility = View.VISIBLE
        itemView.cl_drag_field_text.visibility = View.GONE
    }

    private fun showTextField() {
        itemView.cl_drag_field_text.visibility = View.VISIBLE
        itemView.ll_drag_field_placeholder.visibility = View.GONE
    }

    private fun showCancelBtn() {
        itemView.iv_cancel.visibility = View.VISIBLE
        itemView.iv_checkmark.visibility = View.GONE
    }

    private fun showCorrectBtn() {
        itemView.iv_checkmark.visibility = View.VISIBLE
        itemView.iv_cancel.visibility = View.GONE
    }
}
