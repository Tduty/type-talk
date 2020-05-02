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
        vhMap[id]?.showSkip()
    }

    fun onViewDetachedFromWindow(id: String) {
        vhMap.remove(id)
    }

    fun setPhrases(phrases: List<PhraseBuildingVO>) {
        this.phrases = phrases.map { it.id to it }.toMap()
    }

    fun addText(id: String, text: String) {
        vhMap[id]?.hiddenSkip()
        buildText[id]?.add(text.trim())
        if (buildText[id] == null) buildText[id] = mutableListOf(text)
        val isCorrectText = buildText[id] == phrases[id]?.phrases?.map { it.trim() }
        vhMap[id]?.updateText(buildText[id]?.joinToString(" ") ?: "", isCorrectText)
        if (isCorrectText) {
            vhMap[id]?.showCorrectState()
            presenter.setCorrectText(id, buildText[id])
        }
        else if (buildText[id]?.size == phrases[id]?.phrases?.size) {
            vhMap[id]?.clearText()
            vhMap[id]?.showMessageAboutWrongOffer()
            buildText[id] = mutableListOf()
            vhMap[id]?.showSkip()
        }
    }

    fun clearText(id: String) {
        vhMap[id]?.showSkip()
        buildText[id] = mutableListOf()
        vhMap[id]?.clearText()
    }

    fun nextPage(id: String) {
        presenter.nextPage(id)
        presenter.setInputText(id, buildText[id])
    }
}