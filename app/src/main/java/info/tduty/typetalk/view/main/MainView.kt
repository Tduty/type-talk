package info.tduty.typetalk.view.main

import info.tduty.typetalk.data.model.LessonVO

/**
 * Created by Evgeniy Mezentsev on 2019-11-20.
 */
interface MainView {

    fun setLessons(lessons: List<LessonVO>)

    fun addLesson(lesson: LessonVO)

    fun openChat(chatId: String)

    fun openTeacherChat()

    fun openClassChat()

    fun openBots()

    fun openLesson(lessonId: String)

    fun openAuthScreen()
}
