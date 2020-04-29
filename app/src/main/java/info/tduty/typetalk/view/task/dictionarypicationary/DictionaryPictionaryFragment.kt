package info.tduty.typetalk.view.task.dictionarypicationary

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
import info.tduty.typetalk.data.model.DictionaryPictionaryVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.utils.KeyboardHelper
import info.tduty.typetalk.utils.alert.AlertDialogItems
import info.tduty.typetalk.utils.alert.AlertDialogItemsVO
import info.tduty.typetalk.utils.alert.TypeAlertItem
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.base.BaseFragment
import info.tduty.typetalk.view.task.StateInputWord
import info.tduty.typetalk.view.task.dictionarypicationary.di.DictionaryPictionaryModule
import kotlinx.android.synthetic.main.fragment_task_dictionary_pictionary.*
import kotlinx.android.synthetic.main.fragment_task_dictionary_pictionary.view.*
import kotlinx.android.synthetic.main.fragment_task_flashcard.btn_next
import kotlinx.android.synthetic.main.item_edittext_enter_word.*
import kotlinx.android.synthetic.main.item_edittext_enter_word.view.*
import kotlinx.android.synthetic.main.item_pager_task_translation.*
import javax.inject.Inject
import kotlin.math.roundToInt

class DictionaryPictionaryFragment : BaseFragment(R.layout.fragment_task_dictionary_pictionary),
    DictionaryPictionaryView {

    companion object {

        private const val ARGUMENT_TASK_VO = "task_vo"

        @JvmStatic
        fun newInstance(taskVO: TaskVO): DictionaryPictionaryFragment {
            val bundle = Bundle()
            bundle.putParcelable(ARGUMENT_TASK_VO, taskVO)
            val fragment = DictionaryPictionaryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var presenter: DictionaryPictionaryPresenter
    private var lessonsId: String = ""
    private var adapter: VpAdapter? = null
    val Int.dp: Float get() = Resources.getSystem().displayMetrics.density

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
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
        setupInput()
        setStateInput(StateInputWord.DEFAULT)

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

    private fun setupToolbar(toolbar: Toolbar, title: String, isShowBackButton: Boolean) {
        toolbar.title = title
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setHomeButtonEnabled(isShowBackButton)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(
            isShowBackButton
        )
    }

    private fun setupListener() {
        view?.setOnClickListener {
            KeyboardHelper.hideKeyboard(activity, view)
        }

        btn_next?.setOnClickListener {
            presenter.onClickNext(vp_dictionary_pictionary.currentItem, et_word.text.toString())
        }

        et_word.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (!hasFocus) {
                    presenter.onChangeEditText(
                        et_word.text.toString(),
                        vp_dictionary_pictionary.currentItem
                    )
                }
            }
        }

        et_word.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                presenter.onChangeEditText(p0.toString(), vp_dictionary_pictionary.currentItem)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        vp_dictionary_pictionary.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
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
        vp_dictionary_pictionary.isUserInputEnabled = false
    }

    private fun setupInput() {
        et_word.hint =
            activity?.resources?.getString(R.string.task_screen_dictionary_pictionary_input_hint)
    }

    private fun setupViewPager() {
        adapter = VpAdapter(requireContext())
        vp_dictionary_pictionary.adapter = adapter
    }

    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(DictionaryPictionaryModule(this))
            .inject(this)
    }

    override fun setTitleNextButton(title: Int) {
        btn_next.text = requireActivity().resources?.getString(title)
    }

    override fun setStateInput(state: StateInputWord) {
        when (state) {
            StateInputWord.DEFAULT -> {
                clearEditText()
                cv_container_word.setBackgroundResource(R.drawable.et_circle_bg_shadow)
                iv_right_top_corner?.visibility = View.GONE
                cv_container_word.et_word.isEnabled = true
            }
            StateInputWord.VALID -> {
                changeBorder(5.dp, R.color.chateau_green)
                cv_container_word.et_word.isEnabled = false
            }
            StateInputWord.EDIT -> {
                cv_container_word.setBackgroundResource(R.drawable.et_circle_bg_shadow)
                iv_right_top_corner?.visibility = View.GONE
                cv_container_word.et_word.isEnabled = true
            }
            StateInputWord.ERROR -> {
            }
        }
    }

    private fun changeBorder(dp: Float, color: Int) {
        cv_container_word.setBackgroundResource(R.drawable.et_circle_bg)
        val shapeDrawable = cv_container_word.background as GradientDrawable
        shapeDrawable.setStroke(dp.roundToInt(), requireContext().resources.getColor(color))
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

    override fun setupDictionaryPictionary(dictionaryPictionaryList: List<DictionaryPictionaryVO>) {
        adapter?.setupDictionaryPictionaryList(dictionaryPictionaryList)
        adapter?.notifyDataSetChanged()
    }

    override fun showWord(position: Int, isAnimated: Boolean) {
        vp_dictionary_pictionary.setCurrentItem(position, isAnimated)
    }

    override fun successCompletedWithIncorrectWord(incorrectWord: List<DictionaryPictionaryVO>) {
        val alert = AlertDialogItems(requireContext())
            .items(getPayloadForAlert(incorrectWord))
            .title(R.string.alert_title_finished_task)
            .firstButtonTitle(R.string.alert_btn_completed)

        alert.setListenerFirstButton {
            presenter.sendEventCompleteTask()
            completeTask()
            alert.dismiss()
        }

        alert.showAlert()
    }

    override fun unsuccessComplete(incorrectWords: List<DictionaryPictionaryVO>) {
        val alert = AlertDialogItems(requireContext())
            .title(R.string.alert_title_failed_task)
            .items(getPayloadForAlert(incorrectWords))
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

    override fun completeTask() {
        (activity as? ViewNavigation)?.closeFragment()
    }

    private fun getPayloadForAlert(dpVO: List<DictionaryPictionaryVO>): List<AlertDialogItemsVO> {
        return dpVO.map {
            AlertDialogItemsVO(
                getTopWord(it.translates),
                it.inputWord ?: "",
                TypeAlertItem.IMAGE,
                it.image
            )
        }
    }

    private fun getTopWord(words: List<String>): String {
        return words.joinToString(prefix = "(", postfix = ")")
    }
}