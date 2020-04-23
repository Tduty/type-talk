package info.tduty.typetalk.view.teacher.manage.dialog.list

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class DialogsPresenter(
    private val view: DialogsView
) {

    fun onCreate() {

    }

    fun onDestroy() {

    }

    fun openChat(chatId: String) {
        view.openDialog(chatId)
    }
}
