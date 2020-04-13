package info.tduty.typetalk.view.task.phrasebuilding

import info.tduty.typetalk.data.model.PhraseBuildingVO

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
interface PhraseBuildingView {

    fun setupPhrases(phrases: List<PhraseBuildingVO>)

    fun nextPage()

    fun openLesson(lessonId: String)
}