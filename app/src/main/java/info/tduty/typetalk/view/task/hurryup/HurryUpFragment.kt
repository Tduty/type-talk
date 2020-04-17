package info.tduty.typetalk.view.task.hurryup

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.uzairiqbal.circulartimerview.CircularTimerListener
import com.uzairiqbal.circulartimerview.TimeFormatEnum
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.HurryUpVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.task.hurryup.di.HurryUpModule
import kotlinx.android.synthetic.main.fragment_task_hurry_up.*
import kotlinx.android.synthetic.main.fragment_task_hurry_up.view.*
import javax.inject.Inject
import kotlin.math.ceil


class HurryUpFragment: Fragment(R.layout.fragment_task_hurry_up), HurryUpView {

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
        setupTimer()

        presenter.onCreate(taskVO)
    }

    private fun setupTimer() {
        // To Initialize Timer
        progress_circular.setCircularTimerListener(object : CircularTimerListener {

            override fun updateDataOnTick(remainingTimeInMs: Long): String {
                return ceil((remainingTimeInMs / 1000f).toDouble()).toString()
            }

            override fun onTimerFinished() {}

        }, 1, TimeFormatEnum.MINUTES, 10)
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
        progress_circular.startTimer()
        adapter.setupHurryUpList(hurryUpList) {
            presenter.onClickListener(it)
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
            completeTask()
            return
        }
        vp_hurry_up.setCurrentItem(vp_hurry_up.currentItem + 1, isAnimated)
    }
}