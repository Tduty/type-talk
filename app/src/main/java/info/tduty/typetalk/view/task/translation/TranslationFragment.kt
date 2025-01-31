package info.tduty.typetalk.view.task.translation

import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.data.model.TranslationVO
import info.tduty.typetalk.data.model.TranslationVO.Companion.PHRASE_TYPE
import info.tduty.typetalk.utils.KeyboardHelper
import info.tduty.typetalk.utils.alert.AlertDialogItems
import info.tduty.typetalk.utils.alert.AlertDialogItemsVO
import info.tduty.typetalk.utils.alert.TypeAlertItem
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.base.BaseFragment
import info.tduty.typetalk.view.task.StateInputWord
import info.tduty.typetalk.view.task.translation.di.TranslationModule
import kotlinx.android.synthetic.main.fragment_task_flashcard.btn_next
import kotlinx.android.synthetic.main.fragment_task_translation.*
import kotlinx.android.synthetic.main.fragment_task_translation.view.*
import kotlinx.android.synthetic.main.item_edittext_enter_word.*
import kotlinx.android.synthetic.main.item_edittext_enter_word.view.*
import kotlinx.android.synthetic.main.item_pager_task_translation.*
import javax.inject.Inject
import kotlin.math.roundToInt


class TranslationFragment : BaseFragment(R.layout.fragment_task_translation), TranslationView {

    companion object {

        private const val ARGUMENT_TASK_VO = "task_vo"

        @JvmStatic
        fun newInstance(taskVO: TaskVO): TranslationFragment {
            val bundle = Bundle()
            bundle.putParcelable(ARGUMENT_TASK_VO, taskVO)
            val fragment = TranslationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var presenter: TranslationPresenter
    lateinit var adapter: ViewPagerAdapter
    private var lessonsId: String = ""

    val Int.dp: Float get() = Resources.getSystem().displayMetrics.density

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(TranslationModule(this))
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
        setStateEditWord(StateInputWord.DEFAULT)

        presenter.onCreate(taskVO)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
        menu.findItem(R.id.action_dictionary)?.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_dictionary -> (activity as? ViewNavigation)?.openDictionary()
            R.id.action_chat -> (activity as? ViewNavigation)?.openTeacherChat()
        }
        return true
    }

    private fun setupListener() {
        view?.setOnClickListener {
            KeyboardHelper.hideKeyboard(activity, view)
        }

        btn_next?.setOnClickListener {
            presenter.onClickNext(vp_translation.currentItem, et_word.text.toString())
        }

        et_word.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (!hasFocus) {
                    presenter.onChangeEditText(et_word.text.toString(), vp_translation.currentItem)
                }
            }
        }

        et_word.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                presenter.onChangeEditText(p0.toString(), vp_translation.currentItem)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        vp_translation.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                presenter.onScrollCard(position)
            }
        })

        // Business rule: swipe blocked
        vp_translation.isUserInputEnabled = false
    }

    private fun setupViewPager() {
        adapter = ViewPagerAdapter()
        vp_translation.adapter = adapter
    }

    private fun setupToolbar(toolbar: Toolbar, title: String, isShowBackButton: Boolean) {
        toolbar.title = title
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setHomeButtonEnabled(isShowBackButton)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(
            isShowBackButton
        )
    }

    override fun setupTranslations(translationList: List<TranslationVO>) {
        adapter.setupTranslationList(translationList)
        adapter.notifyDataSetChanged()
    }

    override fun showWord(position: Int, isAnimated: Boolean) {
        vp_translation.setCurrentItem(position, isAnimated)
    }

    override fun setTitleNextButton(title: Int) {
        btn_next.text = activity?.resources?.getString(title)
    }

    override fun setStateEditWord(state: StateInputWord) {
        when (state) {
            StateInputWord.DEFAULT -> {
                clearEditText()
                cv_container_word.setBackgroundResource(R.drawable.et_circle_bg_shadow)
                cv_container_word.et_word.isEnabled = true

            }
            StateInputWord.VALID -> {
                changeBorder(5.dp, R.color.chateau_green)
                cv_container_word.et_word.isEnabled = false
                adapter.notifyDataSetChanged()
            }
            StateInputWord.EDIT -> {
                cv_container_word.setBackgroundResource(R.drawable.et_circle_bg_shadow)
                cv_container_word.et_word.isEnabled = true
            }
            StateInputWord.ERROR -> {
                changeBorder(5.dp, R.color.guardsman_red)
                cv_container_word?.et_word?.isEnabled = true
            }
        }
    }

    override fun setValueToInput(value: String) {
        et_word.setText(value)
    }

    private fun changeBorder(dp: Float, color: Int) {
        cv_container_word?.let {
            it.setBackgroundResource(R.drawable.et_circle_bg)
            val shapeDrawable = it.background as GradientDrawable
            shapeDrawable.setStroke(dp.roundToInt(), requireContext().resources.getColor(color))
        }
    }

    private fun clearEditText() {
        et_word.text.clear()
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

    override fun hiddenKeyboard() {
        KeyboardHelper.hideKeyboard(activity, view)
    }

    override fun completeTask() {
        (activity as? ViewNavigation)?.closeFragment()
    }

    override fun successCompletedWithIncorrectWord(incorrectWord: List<TranslationVO>) {
        val alert = AlertDialogItems(requireContext())
            .title(R.string.alert_title_finished_task)
            .cancelable(true)
            .canceledOnTouchOutside(true)
            .items(getPayloadForAlert(incorrectWord))
            .firstButtonTitle(R.string.alert_btn_completed)

        alert.setListenerFirstButton {
            presenter.sendEventCompleteTask()
            completeTask()
            alert.dismiss()
        }

        alert.showAlert()
    }

    override fun unsuccessComplete(incorrectWord: List<TranslationVO>) {
        val alert = AlertDialogItems(requireContext())
            .title(R.string.alert_title_failed_task)
            .cancelable(true)
            .canceledOnTouchOutside(true)
            .items(getPayloadForAlert(incorrectWord))
            .firstButtonTitle(R.string.alert_btn_try_again)
            .secondButtonTitle(R.string.alert_btn_completed)

        alert.setListenerFirstButton {
            presenter.tryAgain()
            alert.dismiss()
        }

        alert.setListenerSecondButton {
            completeTask()
            alert.dismiss()
        }

        alert.showAlert()
    }

    private fun getPayloadForAlert(traslationList: List<TranslationVO>): List<AlertDialogItemsVO> {
        val INFO_WAIT_TEACHER_ALERT = requireContext().resources.getString(R.string.alert_item_information_wait_teacher)
        return traslationList.map {
            AlertDialogItemsVO(
                if (it.type == PHRASE_TYPE) it.word else "${it.word} ($INFO_WAIT_TEACHER_ALERT)",
                it.inputWord ?: "",
                if (it.type == PHRASE_TYPE) TypeAlertItem.ERROR else TypeAlertItem.INFO
            )
        }
    }
}