package info.tduty.typetalk.view.login.password

/**
 * Created by Evgeniy Mezentsev on 11.03.2020.
 */
interface LoginView {

    fun showProgress()

    fun hideProgress()

    fun setClickableBtn(isClickable: Boolean)

    fun openMainScreen()

    fun showError()
}
