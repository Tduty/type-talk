package info.tduty.typetalk.view.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.LessonVO
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.item_chats_control_block.*

/**
 * Created by Evgeniy Mezentsev on 2019-11-20.
 */
class MainFragment : Fragment(R.layout.fragment_main), MainView {

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private lateinit var rvLessonsAdapter: RvLessonsAdapter
    private lateinit var presenter: MainPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        presenter.onAttach(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupListeners()
        setupRv()

        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    //region main

    private fun setupListeners() {
        cl_your_teacher_chat.setOnClickListener { presenter.openTeacherChat() }
        cl_your_class_chat.setOnClickListener { presenter.openClassChat() }
        cl_your_bot_chat.setOnClickListener { presenter.openBotChat() }
    }

    private fun setupRv() {
        rvLessonsAdapter = RvLessonsAdapter { presenter.openLesson(it) }
        rv_lessons.layoutManager = LinearLayoutManager(context)
        rv_lessons.adapter = rvLessonsAdapter
    }

    //endregion

    override fun setLessons(lessons: List<LessonVO>) {
        rvLessonsAdapter.setLessons(lessons)
    }

    override fun addLesson(lesson: LessonVO) {
        rvLessonsAdapter.addLesson(lesson)
    }
}
