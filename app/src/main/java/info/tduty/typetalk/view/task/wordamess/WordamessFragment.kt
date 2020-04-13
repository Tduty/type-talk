package info.tduty.typetalk.view.task.wordamess

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.data.model.WordamessVO
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.task.flashcard.FlashcardFragment
import info.tduty.typetalk.view.task.wordamess.di.WordamessModule
import kotlinx.android.synthetic.main.fragment_dictionary.view.*
import kotlinx.android.synthetic.main.fragment_wordamess.*
import javax.inject.Inject


/**
 * Created by Evgeniy Mezentsev on 11.04.2020.
 */
class WordamessFragment : Fragment(R.layout.fragment_wordamess), WordamessView {

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

    override fun setupTasks(list: List<WordamessVO>) {
        list.forEach {
            fl_chip.addChip(it.body) { isSelected: Boolean, body: String ->
                if (isSelected) presenter.selectWord(body)
                else presenter.deselectWord(body)
            }
        }
    }

    override fun setCorrectWordCount(count: Int) {
        if (count == 0) btn_correct.text = requireContext().resources.getString(R.string.task_screen_wordamess_btn_correct)
        else btn_correct.text = requireContext().resources.getString(R.string.task_screen_wordamess_btn_correct_with_num, count)
    }

    override fun startCorrectWords(list: List<WordamessVO>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setClickableBtn(isClickable: Boolean) {
        btn_correct.isEnabled = isClickable
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
    }
}
