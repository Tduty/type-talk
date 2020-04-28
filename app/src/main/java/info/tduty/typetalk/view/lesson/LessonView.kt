package info.tduty.typetalk.view.lesson

import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.view.chat.ChatStarter

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */
interface LessonView {

    fun setToolbarTitle(title: String)

    fun setTasks(tasks: List<TaskVO>)

    fun openFlashcardTask(taskVO: TaskVO)

    fun openWordamessTask(taskVO: TaskVO)

    fun openHurryUpTask(taskVO: TaskVO)

    fun openPhraseBuilderTask(taskVO: TaskVO)

    fun openTranslationTask(taskVO: TaskVO)

    fun openDictionaryPictionary(taskVO: TaskVO)

    fun showDialogSearchView()

    fun hideDialogSearchView()

    fun openDialogTask(chatStarter: ChatStarter)

    fun showFindDialogError()
}