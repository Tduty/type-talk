package info.tduty.typetalk.view.teacher.classinfo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.StudentVO
import info.tduty.typetalk.view.MainActivity
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.base.BaseFragment
import info.tduty.typetalk.view.teacher.classinfo.di.ClassModule
import kotlinx.android.synthetic.main.fragment_class.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.item_class_control_block.*
import javax.inject.Inject

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
class ClassFragment : BaseFragment(R.layout.fragment_class), ClassView {

    companion object {

        private const val ARGUMENT_CLASS_ID = "class_id"
        private const val ARGUMENT_CLASS_NAME = "class_name"

        @JvmStatic
        fun newInstance(classId: String, className: String?): ClassFragment {
            val bundle = Bundle()
            bundle.putString(ARGUMENT_CLASS_ID, classId)
            bundle.putString(ARGUMENT_CLASS_NAME, className)
            val fragment = ClassFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
    @Inject
    lateinit var presenter: ClassPresenter
    private lateinit var adapter: RvStudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val classId = arguments?.getString(ARGUMENT_CLASS_ID)
            ?: throw IllegalArgumentException("class id is null")
        val className = arguments?.getString(ARGUMENT_CLASS_NAME) ?: ""

        (activity as? MainActivity)?.setupToolbar(view.toolbar as Toolbar, className, true)
        setupRv()
        setupListeners()

        presenter.onCreate(classId)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(ClassModule(this))
            .inject(this)
    }

    private fun setupRv() {
        adapter = RvStudentAdapter { chatId -> presenter.openChat(chatId) }
        rv_students.layoutManager = LinearLayoutManager(context)
        rv_students.adapter = adapter
    }

    private fun setupListeners() {
        cl_manage_lesson.setOnClickListener { presenter.openManageLessons() }
        cl_class.setOnClickListener { presenter.openClassChat() }
    }

    override fun setToolbar(title: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
    }

    override fun setStudents(students: List<StudentVO>) {
        adapter.setStudents(students)
    }

    override fun updateStatus(studentId: String, status: StudentVO.Status) {
        adapter.updateStatus(studentId, status)
    }

    override fun updateAction(studentId: String, action: String) {
        adapter.updateAction(studentId, action)
    }

    override fun updateExistNewMessages(chatId: String, exist: Boolean) {
        adapter.updateExistNewMessages(chatId, exist)
    }

    override fun openChat(chatId: String) {
        (activity as? ViewNavigation)?.openChat(chatId)
    }

    override fun openManageLessons(classId: String) {
        (activity as? ViewNavigation)?.openManageLessons(classId)
    }

    override fun openClassChat() {
        (activity as? ViewNavigation)?.openClassChat()
    }
}