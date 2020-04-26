package info.tduty.typetalk.view.task.wordamess

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
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.data.model.WordamessVO
import info.tduty.typetalk.utils.KeyboardHelper
import info.tduty.typetalk.utils.alert.AlertDialogItems
import info.tduty.typetalk.utils.alert.AlertDialogItemsVO
import info.tduty.typetalk.utils.alert.TypeAlertItem
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.base.BaseFragment
import info.tduty.typetalk.view.task.wordamess.di.WordamessModule
import kotlinx.android.synthetic.main.fragment_wordamess.*
import kotlinx.android.synthetic.main.fragment_wordamess.view.*
import kotlinx.android.synthetic.main.item_edittext_enter_word.*
import kotlinx.android.synthetic.main.item_task_card_content_word.*
import javax.inject.Inject
import kotlin.math.roundToInt


/**
 * Created by Evgeniy Mezentsev on 11.04.2020.
 */
class WordamessFragment : BaseFragment(R.layout.fragment_wordamess), WordamessView {

    companion object {

        private const val ARGUMENT_TASK_VO = "task_vo"

        @JvmStatic
        fun newInstance(taskVO: TaskVO): WordamessFragment {
            val bundle = Bundle()
            bundle.putParcelable(ARGUMENT_TASK_VO, taskVO)
            val fragment = WordamessFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var presenter: WordamessPresenter

    lateinit var adapter: ViewPagerAdapter

    private val Int.dp: Float get() =  Resources.getSystem().displayMetrics.density

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskVO = arguments?.getParcelable<TaskVO>(ARGUMENT_TASK_VO)
            ?: throw IllegalArgumentException("task is null")

        setupToolbar(view.toolbar as Toolbar, taskVO.title, true)
        setHasOptionsMenu(true)

        setupViewPager()
        setupInputHint()
        setClickableBtn(false)
        setupListeners()

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
            .plus(WordamessModule(this))
            .inject(this)
    }

    private fun setupViewPager() {
        adapter = ViewPagerAdapter()
        vp_correct.adapter = adapter
    }

    private fun setupInputHint() {
        et_word.setHint(R.string.task_screen_wordamess_input_hint)
    }

    override fun showChosenBlock() {
        cl_correct_block.visibility = View.GONE
        cl_chosen_block.visibility = View.VISIBLE
    }

    override fun showCorrectBlock() {
        cl_chosen_block.visibility = View.GONE
        cl_correct_block.visibility = View.VISIBLE
    }

    override fun setupTasksForChoose(list: List<WordamessVO>) {
        fl_chip.clearAll()
        list.forEach {
            fl_chip.addChip(it.body) { isSelected: Boolean, body: String ->
                if (isSelected) presenter.selectWord(body)
                else presenter.deselectWord(body)
            }
        }
    }

    override fun setupTasksForCorrect(list: List<WordamessVO>) {
        adapter.setupWordamessList(list)
    }

    override fun showWordForCorrect(position: Int) {
        vp_correct.setCurrentItem(position, true)
    }

    override fun setCorrectWordCount(count: Int) {
        if (count == 0) btn_correct.text = requireContext().resources.getString(R.string.task_screen_wordamess_btn_correct)
        else btn_correct.text = requireContext().resources.getString(R.string.task_screen_wordamess_btn_correct_with_num, count)
    }

    override fun setupTitleNextBtn() {
        btn_next.setText(R.string.button_next)
    }

    override fun setupTitleSkipBtn() {
        btn_next.setText(R.string.button_skip)
    }

    override fun setupTitleCompletedBtn() {
        btn_next.setText(R.string.button_complete)
    }

    override fun setStateEditWord(state: WordamessView.StateEditWord) {
        when (state) {
            WordamessView.StateEditWord.DEFAULT -> {
                cv_container_word.setBackgroundResource(R.drawable.et_circle_bg_shadow)
                iv_right_top_corner?.visibility = View.GONE
            }
            WordamessView.StateEditWord.VALID -> {
                changeBorder(5.dp, R.color.chateau_green)
                iv_right_top_corner?.visibility = View.VISIBLE
            }
            WordamessView.StateEditWord.EDIT -> {
                cv_container_word.setBackgroundResource(R.drawable.et_circle_bg_shadow)
                iv_right_top_corner?.visibility = View.GONE
            }
        }
    }

    override fun setValueToInput(value: String) {
        et_word.setText(value)
    }

    override fun hiddenKeyboard() {
        KeyboardHelper.hideKeyboard(activity, view)
    }

    override fun setClickableBtn(isClickable: Boolean) {
        btn_correct.isEnabled = isClickable
    }

    override fun completeTask() {
        (activity as? ViewNavigation)?.closeFragment()
    }

    override fun showErrorExistCorrectWords() {
        view?.let {
            Snackbar.make(it, getString(R.string.task_screen_wordamess_error_selected_no_error), Snackbar.LENGTH_LONG)
                .show()
        }
    }

    override fun showErrorTasksEmpty() {
        view?.let {
            Snackbar.make(it, getString(R.string.task_screen_empty_error), Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun setupListeners() {
        btn_correct.setOnClickListener { presenter.onCorrect() }
        btn_next.setOnClickListener {
            presenter.onClickNext(vp_correct.currentItem)
        }

        et_word.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (!hasFocus) {
                    presenter.onChangeEditText(et_word.text.toString(), vp_correct.currentItem)
                }
            }
        }
        et_word.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                presenter.onChangeEditText(p0.toString(), vp_correct.currentItem)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        vp_correct.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
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
        vp_correct.isUserInputEnabled = false
    }

    private fun changeBorder(dp: Float, color: Int) {
        cv_container_word.setBackgroundResource(R.drawable.et_circle_bg)
        val shapeDrawable =  cv_container_word.background as GradientDrawable
        shapeDrawable.setStroke(dp.roundToInt(), requireContext().resources.getColor(color))
    }

    override fun successCompletedWithIncorrectWord(skippedWord: List<WordamessVO>) {
        val alert = AlertDialogItems(requireContext())
            .title(R.string.alert_title_finished_task)
            .items(getPayloadForAlert(skippedWord))
            .firstButtonTitle(R.string.task_btn_complete)

        alert.setListenerFirstButton {
            presenter.sendEventCompleteTask()
            completeTask()
            alert.dismiss()
        }

        alert.showAlert()    }

    override fun unsuccessComplete(skippedWord: List<WordamessVO>) {
        val alert = AlertDialogItems(requireContext())
            .title(R.string.alert_title_finished_task)
            .items(getPayloadForAlert(skippedWord))
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

        alert.showAlert()    }

    private fun getPayloadForAlert(dpVO: List<WordamessVO>): List<AlertDialogItemsVO> {
        return dpVO.map {
            AlertDialogItemsVO(
                it.body,
                it.inputText,
                TypeAlertItem.ERROR
            )
        }
    }
}
