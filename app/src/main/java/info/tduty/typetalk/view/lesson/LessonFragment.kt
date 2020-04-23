package info.tduty.typetalk.view.lesson

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.view.MainActivity
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.chat.ChatStarter
import info.tduty.typetalk.view.lesson.di.LessonsModule
import kotlinx.android.synthetic.main.alert_dialog_information.view.*
import kotlinx.android.synthetic.main.alert_dialog_search.view.*
import kotlinx.android.synthetic.main.fragment_lesson.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import javax.inject.Inject


class LessonFragment : Fragment(R.layout.fragment_lesson), LessonView {

    companion object {

        private const val ARGUMENT_LESSON_ID = "lesson_id"

        @JvmStatic
        fun newInstance(lessonId: String): LessonFragment {
            val bundle = Bundle()
            bundle.putString(ARGUMENT_LESSON_ID, lessonId)
            val fragment = LessonFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
    @Inject
    lateinit var presenter: LessonPresenter
    private lateinit var adapter: RvTasksAdapter
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lessonId = arguments?.getString(ARGUMENT_LESSON_ID)
            ?: throw IllegalArgumentException("lesson id is null")

        setupRv()
        (activity as? MainActivity)?.setupToolbar(view.toolbar as Toolbar, R.string.empty_string, true)
        setHasOptionsMenu(true)

        presenter.onCreate(lessonId)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_lessons, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_dictionary -> (activity as? ViewNavigation)?.openDictionary()
            R.id.action_chat -> (activity as? ViewNavigation)?.openTeacherChat()
        }
        return true
    }

    override fun setToolbarTitle(title: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
    }

    override fun setTasks(tasks: List<TaskVO>) {
        adapter.setTasks(tasks)
    }

    override fun openFlashcardTask(taskVO: TaskVO) {
        (activity as? ViewNavigation)?.openFlashcardTask(taskVO)
    }

    override fun openWordamessTask(taskVO: TaskVO) {
        (activity as? ViewNavigation)?.openWordamessTask(taskVO)
    }

    override fun openHurryUpTask(taskVO: TaskVO) {
        (activity as? ViewNavigation)?.openHurryUpTask(taskVO)
    }

    override fun openPhraseBuilderTask(taskVO: TaskVO) {
        (activity as? ViewNavigation)?.openPhaserBuilderTask(taskVO)
    }

    override fun openTranslationTask(taskVO: TaskVO) {
        (activity as? ViewNavigation)?.openTranslationTask(taskVO)
    }

    override fun openDictionaryPictionary(taskVO: TaskVO) {
        (activity as? ViewNavigation)?.openDictionaryPictionary(taskVO)
    }

    override fun showDialogSearchView() {
        setupSearchView()
    }

    override fun hideDialogSearchView() {
        alertDialog?.dismiss()
    }

    override fun openDialogTask(chatStarter: ChatStarter) {
        (activity as? ViewNavigation)?.openChat(chatStarter)
    }

    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(LessonsModule(this))
            .inject(this)
    }

    private fun setupRv() {
        adapter = RvTasksAdapter { id, type ->  presenter.openTask(id, type) }
        rv_tasks.layoutManager = LinearLayoutManager(context)
        rv_tasks.adapter = adapter
    }

    private fun setupSearchView() {
        val mDialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog_search, null)
        val mBuilder = AlertDialog.Builder(requireContext()).setView(mDialogView)
        alertDialog = mBuilder.show()

        alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.setOnCancelListener { presenter.cancelOpenDialog() }

        mDialogView.tv_description.setText(R.string.lesson_screen_search_dialog) //TODO
    }
}
