package info.tduty.typetalk.view.lesson

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.lesson.di.LessonsModule
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_dictionary.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.toolbar_lesson.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lessonId = arguments?.getString(ARGUMENT_LESSON_ID)
            ?: throw IllegalArgumentException("lesson id is null")

        setupFragmentComponent()
        setupRv()
        setupToolbar()

        presenter.onCreate(lessonId)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun setToolbarTitle(title: String) {
        tv_toolbar_title.title.text = title
    }

    override fun setTasks(tasks: List<TaskVO>) {
        adapter.setTasks(tasks)
    }

    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(LessonsModule(this))
            .inject(this)
    }

    private fun setupRv() {
        adapter = RvTasksAdapter { id, type ->  presenter.openTask(id, type) }
        rv_lessons.layoutManager = LinearLayoutManager(context)
        rv_lessons.adapter = adapter
    }


    private fun setupToolbar() {
        iv_back_button.setOnClickListener { activity?.onBackPressed() }
        iv_chat_button.setOnClickListener { (activity as? ViewNavigation)?.openChat("chat_teacher") } // TODO: open base chat?!
        iv_dictionary_button.setOnClickListener {  } // TODO: open dictionary for lesson
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar_chat as Toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}
