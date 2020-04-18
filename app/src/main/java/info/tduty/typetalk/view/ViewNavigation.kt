package info.tduty.typetalk.view


import info.tduty.typetalk.data.model.TaskVO
/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
interface ViewNavigation {

    fun closeFragment()

    fun openMain()

    fun openClass(classId: String, className: String)

    fun openChat(chatId: String)

    fun openTeacherChat()

    fun openClassChat()

    fun openBots()

    fun openLesson(lessonId: String)

    fun openDictionary()

    fun openQRAuth()

    fun openLoginAuth()

    fun openFlashcardTask(taskVO: TaskVO)

    fun openTranslationTask(taskVO: TaskVO)

    fun openDictionaryPictionary(taskVO: TaskVO)

    fun openWordamessTask(taskVO: TaskVO)

    fun openPhaserBuilderTask(taskVO: TaskVO)

    fun openHurryUpTask(taskVO: TaskVO)
}
