package info.tduty.typetalk.view.task.hurryup

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.HurryUpVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.utils.timer.CircleAngleAnimation
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.base.BaseFragment
import info.tduty.typetalk.view.task.hurryup.di.HurryUpModule
import kotlinx.android.synthetic.main.alert_dialog_information.view.*
import kotlinx.android.synthetic.main.fragment_task_hurry_up.*
import kotlinx.android.synthetic.main.fragment_task_hurry_up.view.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class HurryUpFragment: BaseFragment(R.layout.fragment_task_hurry_up), HurryUpView {

    companion object {

        private const val ARGUMENT_TASK_VO = "task_vo"

        @JvmStatic
        fun newInstance(taskVO: TaskVO) : HurryUpFragment {
            val bundle = Bundle()
            bundle.putParcelable(ARGUMENT_TASK_VO, taskVO)
            val fragment = HurryUpFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var presenter: HurryUpPresenter
    lateinit var adapter: VpAdapter

    private var lessonsId: String = ""
    private var anim: CircleAngleAnimation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(HurryUpModule(this))
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskVO = arguments?.getParcelable<TaskVO>(ARGUMENT_TASK_VO)
            ?: throw IllegalArgumentException("task is null")
        this.lessonsId = taskVO.lessonId

        setupToolbar(view.toolbar as Toolbar, taskVO.title, true)
        setHasOptionsMenu(true)

        setupViewPager()
        setupListener()
        setupTimer(60)

        presenter.onCreate(taskVO)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    private fun setupTimer(seconds: Long) {
        anim = CircleAngleAnimation(circle,
            0,
            seconds * 1000,
            1000,
            { miliSeconds -> timer_text.text = getFormatedTime(miliSeconds) },
            { presenter.completedTask() })
        anim?.setAnimationListener(anim)
    }

    fun getFormatedTime(remainingTimeInMs: Long): String {
       return String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(remainingTimeInMs) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(remainingTimeInMs)),
            TimeUnit.MILLISECONDS.toSeconds(remainingTimeInMs) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTimeInMs)))
    }

    private fun setupListener() {
        // Business rule: swipe blocked
        vp_hurry_up.isUserInputEnabled = false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_dictionary -> (activity as? ViewNavigation)?.openDictionary()
            R.id.action_chat -> (activity as? ViewNavigation)?.openTeacherChat()
        }
        return true
    }

    private fun setupToolbar(toolbar: Toolbar, title: String, isShowBackButton: Boolean) {
        toolbar.title = title
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setHomeButtonEnabled(isShowBackButton)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(
            isShowBackButton
        )
    }

    private fun setupViewPager() {
        adapter = VpAdapter()
        vp_hurry_up.adapter = adapter
    }

    override fun completeTask() {
        (activity as? ViewNavigation)?.closeFragment()
    }

    override fun setupHurryUp(hurryUpList: List<HurryUpVO>) {
        adapter.setupHurryUpList(hurryUpList) { selectedWord, hurryUpVO ->
            presenter.onSelectWord(selectedWord, hurryUpVO)
        }
    }

    override fun showError() {
        view?.let {
            Snackbar.make(
                it,
                getString(R.string.auth_screen_error_authorization),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun nextPage(isAnimated: Boolean) {
        if (vp_hurry_up.currentItem == adapter.itemCount - 1) {
            presenter.completedTask()
            return
        }
        vp_hurry_up.setCurrentItem(vp_hurry_up.currentItem + 1, isAnimated)
    }

    override fun showCompleteAlertDialog(title: Int, message: Int, countCompletedTask: Int, isTryAgain: Boolean) {
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog_information, null)
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(mDialogView)
        val  mAlertDialog = mBuilder.show()

        mAlertDialog.setCancelable(false)
        mAlertDialog.setCanceledOnTouchOutside(false)
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val titleString = resources.getString(title)
        val messageString = "${resources.getString(message)} \nCount completed task: $countCompletedTask"
        val titleFirstButton = resources.getString(R.string.task_screen_hurry_up_title_first_button_completed_alert_dialog)
        val titleSecondButton = resources.getString(R.string.task_screen_hurry_up_title_second_button_completed_alert_dialog)

        mDialogView.tv_title.text = titleString
        mDialogView.tv_message.text = messageString
        if (isTryAgain) {
            mDialogView.btn_first_button.visibility = View.VISIBLE
            mDialogView.btn_first_button.text = titleFirstButton
            mDialogView.btn_first_button.setOnClickListener {
                presenter.tryAgain()
                mAlertDialog.dismiss()
            }
        } else {
            mDialogView.btn_first_button.visibility = View.GONE
        }
        mDialogView.btn_second_button.text = titleSecondButton
        //TODO: заменить alert
        mDialogView.btn_second_button.setOnClickListener {
            if (!isTryAgain) {
                presenter.sendEventCompleteTask()
            }
            completeTask()
            mAlertDialog.dismiss()
        }
    }

    override fun showStartAlertDialog(title: Int, message: Int) {
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog_information, null)
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(mDialogView)
        val  mAlertDialog = mBuilder.show()

        mAlertDialog.setCancelable(false)
        mAlertDialog.setCanceledOnTouchOutside(false)
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val titleString = resources.getString(title)
        val messageString = resources.getString(message)
        val titleFirstButton = resources.getString(R.string.task_screen_hurry_up_title_first_button_start_alert_dialog)
        val titleSecondButton = resources.getString(R.string.task_screen_hurry_up_title_second_button_start_alert_dialog)

        mDialogView.tv_title.text = titleString
        mDialogView.tv_message.text = messageString
        mDialogView.btn_first_button.text = titleFirstButton
        mDialogView.btn_first_button.setOnClickListener {
            completeTask()
            mAlertDialog.dismiss()
        }
        mDialogView.btn_second_button.text = titleSecondButton
        mDialogView.btn_second_button.setOnClickListener {
            presenter.startTask()
            mAlertDialog.dismiss()
        }
    }

    override fun startTimer() {
        circle.startAnimation(anim)
    }

    override fun stopTimer() {
        anim?.stop()
    }

    override fun setPenatlyForTimer(second: Int) {
        anim?.setPenatly(second)
    }
}