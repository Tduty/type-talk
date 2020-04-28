package info.tduty.typetalk.view.login.password

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.base.BaseFragment
import info.tduty.typetalk.view.login.password.di.LoginModule
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

/**
 * Created by Evgeniy Mezentsev on 11.03.2020.
 */
class LoginFragment : BaseFragment(R.layout.fragment_login), LoginView {

    companion object {

        @JvmStatic
        fun newInstance() = LoginFragment()
    }

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()

        presenter.onCreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.onDestroy()
    }

    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(LoginModule(this))
            .inject(this)
    }

    override fun showProgress() {
        pb_progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pb_progress.visibility = View.GONE
    }

    override fun setClickableBtn(isClickable: Boolean) {
        btn_next.isClickable = true
    }

    override fun openMainScreen() {
        (activity as? ViewNavigation)?.openMain()
    }

    override fun showError() {
        view?.let {
            Snackbar.make(it, getString(R.string.auth_screen_error_authorization), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setupListeners() {
        iv_qr_code.setOnClickListener { (activity as? ViewNavigation)?.openQRAuth() }
        btn_next.setOnClickListener {
            btn_next.isClickable = false
            presenter.onNext(et_login.text.toString(), et_password.text.toString())
        }
    }
}
