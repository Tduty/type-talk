package info.tduty.typetalk.view

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
interface ViewNavigation {

    fun openMain()

    fun openChat(chatId: String)

    fun openLesson(lessonId: String)

    fun openDictionary()
}
