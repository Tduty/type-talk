package info.tduty.typetalk.view.teacher.manage.tasks

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.DialogVO
import info.tduty.typetalk.data.model.LessonManageVO
import info.tduty.typetalk.data.model.TaskManageVO
import info.tduty.typetalk.view.MainActivity
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.base.BaseFragment
import info.tduty.typetalk.view.teacher.manage.tasks.di.TasksManageModule
import kotlinx.android.synthetic.main.alert_dialog_search.view.*
import kotlinx.android.synthetic.main.fragment_tasks_manage.*
import javax.inject.Inject

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class TasksManageFragment : BaseFragment(R.layout.fragment_tasks_manage), TasksManageView {

    companion object {

        private const val ARGUMENT_CLASS_ID = "class_id"
        private const val ARGUMENT_LESSON = "lesson"

        @JvmStatic
        fun newInstance(classId: String, lesson: LessonManageVO): TasksManageFragment {
            val bundle = Bundle()
            bundle.putString(ARGUMENT_CLASS_ID, classId)
            bundle.putParcelable(ARGUMENT_LESSON, lesson)
            val fragment = TasksManageFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
    @Inject
    lateinit var presenter: TasksManagePresenter
    private lateinit var adapter: RvTaskManageAdapter
    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val classId = arguments?.getString(ARGUMENT_CLASS_ID)
            ?: throw IllegalArgumentException("class id is null")
        val lesson = arguments?.getParcelable<LessonManageVO>(ARGUMENT_LESSON)
            ?: throw IllegalArgumentException("lesson is null")

        (activity as? MainActivity)?.setupToolbar(tlb_tasks, true)
        setupRv()

        presenter.onCreate(classId, lesson)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(TasksManageModule(this))
            .inject(this)
    }

    private fun setupRv() {
        adapter = RvTaskManageAdapter { presenter.manageTask(it) }
        rv_tasks.layoutManager = LinearLayoutManager(context)
        rv_tasks.adapter = adapter
    }

    override fun setupLessonIcon(iconRes: Int) {
        iv_icon.setImageResource(iconRes)
    }

    override fun setupLessonTitle(title: String) {
        tv_title.text = title
        ctl_lesson.title = title
    }

    override fun setupLessonCompleted(studentCount: Int, completedCount: Int) {
        when {
            studentCount == 0 -> {
                ppv_lesson_status.visibility = View.GONE
                tv_completed.visibility = View.GONE
            }
            completedCount == 0 -> {
                ppv_lesson_status.visibility = View.GONE
                tv_completed.text =
                    getString(R.string.completed_progress, completedCount, studentCount)
            }
            else -> {
                ppv_lesson_status.visibility = View.VISIBLE
                ppv_lesson_status.progress = completedCount * 100 / studentCount
                tv_completed.text =
                    getString(R.string.completed_progress, completedCount, studentCount)
            }
        }
    }

    override fun setTasks(tasks: List<TaskManageVO>) {
        adapter.setTasks(tasks)
    }

    override fun openDialogs(dialogs: List<DialogVO>) {
        (activity as? ViewNavigation)?.openDialogs(dialogs)
    }

    override fun openChooseStudentsForDialogs(classId: String, lessonId: String, taskId: String) {
        (activity as? ViewNavigation)?.openChooseStudentForDialog(classId, lessonId, taskId)
    }

    override fun showSearchView() {
        setupSearchView()
    }

    override fun hideSearchView() {
        dialog?.dismiss()
    }

    private fun setupSearchView() {
        val mDialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog_search, null)
        val mBuilder = AlertDialog.Builder(requireContext()).setView(mDialogView)
        dialog = mBuilder.show()

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setOnCancelListener {
            presenter.cancelOpenDialogs()
        }

        mDialogView.tv_description.setText(R.string.lesson_screen_search_dialog)
    }
}
