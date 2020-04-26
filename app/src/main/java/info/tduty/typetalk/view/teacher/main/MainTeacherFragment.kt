package info.tduty.typetalk.view.teacher.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.ClassVO
import info.tduty.typetalk.view.MainActivity
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.base.BaseFragment
import info.tduty.typetalk.view.teacher.main.di.MainTeacherModule
import kotlinx.android.synthetic.main.fragment_lesson.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import javax.inject.Inject

/**
 * Created by Evgeniy Mezentsev on 18.04.2020.
 */
class MainTeacherFragment : BaseFragment(R.layout.fragment_main_teacher), MainTeacherView {

    companion object {

        @JvmStatic
        fun newInstance(): MainTeacherFragment {
            return MainTeacherFragment()
        }
    }
    @Inject
    lateinit var presenter: MainTeacherPresenter
    private lateinit var adapter: RvClassesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.setupToolbar(view.toolbar as Toolbar, R.string.app_name, false)
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
            .plus(MainTeacherModule(this))
            .inject(this)
    }

    private fun setupRv() {
        adapter = RvClassesAdapter { id ->
            presenter.openClass(id)
        }
        rv_tasks.layoutManager = LinearLayoutManager(context)
        rv_tasks.adapter = adapter
    }

    override fun setClasses(classes: List<ClassVO>) {
        adapter.setClasses(classes)
    }

    override fun openClassScreen(classId: String, className: String) {
        (activity as? ViewNavigation)?.openClass(classId, className)
    }
}
