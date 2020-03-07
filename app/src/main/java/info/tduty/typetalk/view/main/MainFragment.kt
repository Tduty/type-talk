package info.tduty.typetalk.view.main

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.LessonVO
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.main.di.MainModule
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.item_chats_control_block.*
import javax.inject.Inject

/**
 * Created by Evgeniy Mezentsev on 2019-11-20.
 */
class MainFragment : Fragment(R.layout.fragment_main), MainView {

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private lateinit var rvLessonsAdapter: RvLessonsAdapter
    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(view.toolbar as Toolbar, R.string.app_name, false)
        setupListeners()
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
            .plus(MainModule(this))
            .inject(this)
    }

    //region main

    private fun setupToolbar(toolbar: Toolbar, @StringRes title: Int, withBackButton: Boolean) {
        toolbar.setTitle(title)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setHomeButtonEnabled(withBackButton)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(withBackButton)
    }

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

    override fun openChat(chatId: String) {
        (activity as? ViewNavigation)?.openChat(chatId)
    }
}
