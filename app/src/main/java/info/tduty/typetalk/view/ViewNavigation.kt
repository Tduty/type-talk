package info.tduty.typetalk.view


import info.tduty.typetalk.data.model.DialogVO
import info.tduty.typetalk.data.model.LessonManageVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.view.chat.ChatStarter

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
interface ViewNavigation {

    fun closeFragment()

    fun openMain()

    fun openClass(classId: String, className: String)

    fun openManageLessons(classId: String)

    fun openManageTasks(classId: String, lesson: LessonManageVO)

    fun openDialogs(dialogs: List<DialogVO>)

    fun openChooseStudentForDialog(classId: String, lessonId: String, taskId: String)

    fun openChat(chatId: String, chatType: String? = null)

    fun openChat(chatStarter: ChatStarter)

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
