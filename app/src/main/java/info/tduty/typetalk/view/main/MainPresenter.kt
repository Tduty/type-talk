package info.tduty.typetalk.view.main

/**
 * Created by Evgeniy Mezentsev on 2019-11-20.
 */
class MainPresenter(val mainView: MainView) {

    fun onCreate() {
        listenerAddLesson()
        getLessons()
    }

    fun onDestroy() {

    }

    fun openTeacherChat() {

    }

    fun openClassChat() {

    }

    fun openBotChat() {

    }

    fun openLesson(lessonId: String) {

    }

    private fun listenerAddLesson() {

    }

    private fun getLessons() {

    }
}
