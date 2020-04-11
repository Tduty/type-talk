package info.tduty.typetalk.view.login.password.qr

interface AuthQRView {

    fun isLoading(): Boolean

    fun showScanCamera()

    fun showLoading()

    fun openMainScreen()

    fun showError()
}
