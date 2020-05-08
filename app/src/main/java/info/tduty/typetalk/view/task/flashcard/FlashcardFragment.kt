package info.tduty.typetalk.view.task.flashcard

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.FlashcardVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.base.BaseFragment
import info.tduty.typetalk.view.task.flashcard.di.FlashcardModule
import kotlinx.android.synthetic.main.alert_dialog_information.view.*
import kotlinx.android.synthetic.main.fragment_dictionary.view.*
import kotlinx.android.synthetic.main.fragment_task_flashcard.*
import javax.inject.Inject


class FlashcardFragment : BaseFragment(R.layout.fragment_task_flashcard),
    FlashcardView {

    companion object {

        private const val ARGUMENT_TASK_VO = "task_vo"

        @JvmStatic
        fun newInstance(taskVO: TaskVO): FlashcardFragment {
            val bundle = Bundle()
            bundle.putParcelable(ARGUMENT_TASK_VO, taskVO)
            val fragment =
                FlashcardFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var presenter: FlashcardPresenter
    lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskVO = arguments?.getParcelable<TaskVO>(ARGUMENT_TASK_VO)
            ?: throw IllegalArgumentException("lesson id is null")

        setupToolbar(view.toolbar as Toolbar, taskVO.title, true)
        setHasOptionsMenu(true)

        setupViewPager()
        setupListener()

        presenter.onCreate(taskVO)
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

    private fun setupToolbar(toolbar: Toolbar, title: String, withBackButton: Boolean) {
        toolbar.title = title
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setHomeButtonEnabled(withBackButton)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(withBackButton)
    }

    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(FlashcardModule(this))
            .inject(this)
    }

    private fun setupListener() {
        btn_next?.setOnClickListener {
            presenter.onClickNext(vp_flashcard.currentItem)
        }

        vp_flashcard.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                presenter.onPageScrolled(position)
            }
        })
    }

    private fun setupViewPager() {
        adapter = ViewPagerAdapter()
        vp_flashcard.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun setupFlashcards(flashcardVO: List<FlashcardVO>) {
        adapter.setupFlashCardVO(flashcardVO)
        adapter.notifyDataSetChanged()
    }

    override fun showWord(position: Int, isAnimated: Boolean) {
        vp_flashcard.adapter.let {
            if (position >= 0 && position < vp_flashcard.adapter!!.itemCount)
                vp_flashcard.setCurrentItem(position, isAnimated)
        }
    }

    override fun setTitleNextButton(title: Int) {
        btn_next.text = activity?.resources?.getString(title)
    }

    override fun completedWithNext() {
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog_information, null)
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(mDialogView)
        val  mAlertDialog = mBuilder.show()

        mAlertDialog.setCancelable(false)
        mAlertDialog.setCanceledOnTouchOutside(false)
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        mDialogView.tv_title.setText(R.string.alert_title_finished_task)
        mDialogView.tv_message.setText(R.string.alert_description_task_flashcards_finished)
            mDialogView.btn_first_button.visibility = View.VISIBLE
            mDialogView.btn_first_button.setText(R.string.alert_btn_completed)
            mDialogView.btn_first_button.setOnClickListener {
                disableUI(false)
                presenter.sendEventCompleteTask()
                completeTask()
                mAlertDialog.dismiss()
            }

        mDialogView.btn_second_button.setText(R.string.alert_btn_next)
        mDialogView.btn_second_button.setOnClickListener {
            disableUI(false)
            presenter.onNextExecuteTask()
            mAlertDialog.dismiss()
        }
    }

    override fun disableUI(isDisable: Boolean) {
        btn_next.isEnabled = !isDisable
        toolbar.isEnabled = !isDisable
    }

    override fun showError() {
        // TODO Проработать флоу ошибки для задания
        view?.let {
            Snackbar.make(
                it,
                getString(R.string.auth_screen_error_authorization),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun completeTask() {
        (activity as? ViewNavigation)?.closeFragment()
    }
}