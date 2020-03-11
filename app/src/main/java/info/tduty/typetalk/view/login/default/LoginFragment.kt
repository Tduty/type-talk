package info.tduty.typetalk.view.login.default

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.view.login.default.di.LoginModule
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFragmentComponent()

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

    override fun openMainScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
