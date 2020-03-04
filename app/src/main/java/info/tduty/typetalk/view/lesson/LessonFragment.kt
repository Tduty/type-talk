package info.tduty.typetalk.view.lesson

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.di.LessonsModule
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class LessonFragment : Fragment(R.layout.fragment_lesson), LessonView {

    companion object {

        @JvmStatic
        fun newInstance() = LessonFragment()
    }
    @Inject
    lateinit var presenter: LessonPresenter
    private lateinit var adapter: RvTasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupFragmentComponent()
        setupRv()

        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
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

    override fun setTasks(tasks: List<TaskVO>) {
        adapter.setTasks(tasks)
    }
}
