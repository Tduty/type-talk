package info.tduty.typetalk.view.login.password

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.login.password.di.LoginModule
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

/**
 * Created by Evgeniy Mezentsev on 11.03.2020.
 */
class LoginFragment : Fragment(R.layout.fragment_login), LoginView {

    companion object {

        @JvmStatic
        fun newInstance() = LoginFragment()
    }

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
        setupWindowModeAdJustPan()
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

    private fun setupWindowModeAdJustPan() {
        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        )
    }

    private fun setupWindowModeAdJustResize() {
        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
    }

    override fun openMainScreen() {
        (activity as? ViewNavigation)?.openMain()
        setupWindowModeAdJustResize()
    }

    override fun showError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun setupListeners() {
        btn_next.setOnClickListener {
            presenter.onNext(et_login.text.toString(), et_password.text.toString())
        }

        iv_qr_code.setOnClickListener {
            (activity as? ViewNavigation)?.openQRAuth()
        }
    }
}
