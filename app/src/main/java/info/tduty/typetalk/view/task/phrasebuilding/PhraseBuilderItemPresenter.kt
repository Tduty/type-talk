package info.tduty.typetalk.view.task.phrasebuilding

import info.tduty.typetalk.data.model.PhraseBuildingVO

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
class PhraseBuilderItemPresenter(
    private val presenter: PhraseBuildingPresenter
) {

    private val vhMap = mutableMapOf<String, PagerVH>()
    private lateinit var phrases: Map<String, PhraseBuildingVO>
    private val buildText: MutableMap<String, MutableList<String>> = hashMapOf()

    fun onViewAttachedToWindow(id: String, vh: PagerVH) {
        vhMap[id] = vh
    }

    fun onViewDetachedFromWindow(id: String) {
        vhMap.remove(id)
    }

    fun setPhrases(phrases: List<PhraseBuildingVO>) {
        this.phrases = phrases.map { it.id to it }.toMap()
    }

    fun addText(id: String, text: String) {
        buildText[id]?.add(text)
        if (buildText[id] == null) buildText[id] = mutableListOf(text)
        val isCorrectText = buildText[id] == phrases[id]?.phrases
        vhMap[id]?.updateText(buildText[id]?.joinToString(" ") ?: "", isCorrectText)
        if (isCorrectText) vhMap[id]?.showCorrectState()
        else if (buildText[id]?.size == phrases[id]?.phrases?.size) {
            vhMap[id]?.clearText()
            vhMap[id]?.showMessageAboutWrongOffer()
            buildText[id] = mutableListOf()
        }
    }

    fun clearText(id: String) {
        buildText[id] = mutableListOf()
        vhMap[id]?.clearText()
    }

    fun nextPage(id: String) {
        presenter.nextPage(id)
    }
}