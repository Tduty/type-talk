package info.tduty.typetalk.view.teacher.manage.dialog.choose

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.ChooseStudentVO
import info.tduty.typetalk.data.model.DialogVO
import info.tduty.typetalk.view.MainActivity
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.teacher.manage.dialog.choose.di.ChooseStudentsModule
import info.tduty.typetalk.view.teacher.manage.dialog.list.DialogsFragment
import info.tduty.typetalk.view.teacher.manage.lessons.LessonsManageFragment
import kotlinx.android.synthetic.main.fragment_choose_students.*
import kotlinx.android.synthetic.main.fragment_choose_students.view.*
import javax.inject.Inject

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class ChooseStudentsFragment : Fragment(R.layout.fragment_choose_students), ChooseStudentsView {

    companion object {

        private const val ARGUMENT_CLASS_ID = "class_id"
        private const val ARGUMENT_LESSON_ID = "lesson_id"
        private const val ARGUMENT_TASK_ID = "task_id"

        @JvmStatic
        fun newInstance(classId: String, lessonId: String, taskId: String): ChooseStudentsFragment {
            val bundle = Bundle()
            bundle.putString(ARGUMENT_CLASS_ID, classId)
            bundle.putString(ARGUMENT_LESSON_ID, lessonId)
            bundle.putString(ARGUMENT_TASK_ID, taskId)
            val fragment = ChooseStudentsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var presenter: ChooseStudentsPresenter
    private lateinit var adapter: RvChooseStudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val classId = arguments?.getString(ARGUMENT_CLASS_ID)
            ?: throw IllegalArgumentException("class id is null")
        val lessonId = arguments?.getString(ARGUMENT_LESSON_ID)
            ?: throw IllegalArgumentException("lesson id is null")
        val taskId = arguments?.getString(ARGUMENT_TASK_ID)
            ?: throw IllegalArgumentException("task id is null")

        (activity as? MainActivity)?.setupToolbar(view.toolbar as Toolbar, R.string.class_managment_lesson_title, true)
        setupRv()
        setupListeners()

        presenter.onCreate(classId, lessonId, taskId)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(ChooseStudentsModule(this))
            .inject(this)
    }

    private fun setupRv() {
        adapter = RvChooseStudentAdapter { studentId -> presenter.clickOnStudent(studentId) }
        rv_students.layoutManager = LinearLayoutManager(context)
        rv_students.adapter = adapter
    }

    private fun setupListeners() {
        cv_form_dialogs.setOnClickListener { presenter.createDialogs() }
        cv_select_all.setOnClickListener { presenter.selectAll() }
    }

    override fun setStudents(students: List<ChooseStudentVO>) {
        adapter.setStudents(students)
    }

    override fun closeView() {
        (activity as? ViewNavigation)?.closeFragment()
    }
}
