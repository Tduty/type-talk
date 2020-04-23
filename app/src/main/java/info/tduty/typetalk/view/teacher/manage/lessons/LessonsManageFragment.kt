package info.tduty.typetalk.view.teacher.manage.lessons

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.LessonManageVO
import info.tduty.typetalk.view.MainActivity
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.teacher.classinfo.ClassFragment
import info.tduty.typetalk.view.teacher.manage.lessons.di.LessonsManageModule
import kotlinx.android.synthetic.main.fragment_lessons_manage.*
import kotlinx.android.synthetic.main.fragment_lessons_manage.view.*
import javax.inject.Inject

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class LessonsManageFragment : Fragment(R.layout.fragment_lessons_manage), LessonsManageView {

    companion object {

        private const val ARGUMENT_CLASS_ID = "class_id"

        @JvmStatic
        fun newInstance(classId: String): LessonsManageFragment {
            val bundle = Bundle()
            bundle.putString(ARGUMENT_CLASS_ID, classId)
            val fragment = LessonsManageFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var presenter: LessonsManagePresenter
    private lateinit var adapter: RvLessonManageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val classId = arguments?.getString(ARGUMENT_CLASS_ID)
            ?: throw IllegalArgumentException("class id is null")

        (activity as? MainActivity)?.setupToolbar(view.toolbar as Toolbar, R.string.class_managment_lesson_title, true)
        setupRv()

        presenter.onCreate(classId)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(LessonsManageModule(this))
            .inject(this)
    }

    private fun setupRv() {
        adapter = RvLessonManageAdapter { presenter.openLesson(it) }
        rv_lessons.layoutManager = LinearLayoutManager(context)
        rv_lessons.adapter = adapter
    }

    override fun setLessons(lessons: List<LessonManageVO>) {
        adapter.setLessons(lessons)
    }

    override fun openManageTasks(classId: String, lesson: LessonManageVO) {
        (activity as? ViewNavigation)?.openManageTasks(classId, lesson)
    }
}
