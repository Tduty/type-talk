package info.tduty.typetalk.view

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
interface ViewNavigation {

    fun openMain()

    fun openChat(chatId: String)

    fun openTeacherChat()

    fun openClassChat()

    fun openBots()

    fun openLesson(lessonId: String)

    fun openDictionary()

    fun openQRAuth()

    fun openLoginAuth()
}
