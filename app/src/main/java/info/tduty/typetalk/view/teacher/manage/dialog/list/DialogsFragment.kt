package info.tduty.typetalk.view.teacher.manage.dialog.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.db.model.ChatEntity
import info.tduty.typetalk.data.model.DialogVO
import info.tduty.typetalk.view.MainActivity
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.base.BaseFragment
import info.tduty.typetalk.view.teacher.manage.dialog.list.di.DialogsModule
import kotlinx.android.synthetic.main.fragment_dialogs.*
import kotlinx.android.synthetic.main.fragment_dialogs.view.*
import javax.inject.Inject

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class DialogsFragment : BaseFragment(R.layout.fragment_dialogs), DialogsView {

    companion object {

        private const val ARGUMENT_DIALOGS = "dialogs"

        @JvmStatic
        fun newInstance(dialogs: List<DialogVO>): DialogsFragment {
            val bundle = Bundle()
            bundle.putParcelableArrayList(ARGUMENT_DIALOGS, ArrayList(dialogs))
            val fragment = DialogsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var presenter: DialogsPresenter
    private lateinit var adapter: RvDialogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialogs = arguments?.getParcelableArrayList<DialogVO>(ARGUMENT_DIALOGS)
            ?: throw IllegalArgumentException("dialogs id is null")

        (activity as? MainActivity)?.setupToolbar(view.toolbar as Toolbar, R.string.class_managment_lesson_title, true)
        setupRv()
        adapter.setDialogs(dialogs)

        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(DialogsModule(this))
            .inject(this)
    }

    private fun setupRv() {
        adapter = RvDialogAdapter { chatId -> presenter.openChat(chatId) }
        rv_dialogs.layoutManager = LinearLayoutManager(context)
        rv_dialogs.adapter = adapter
    }

    override fun openDialog(chatId: String) {
        (activity as? ViewNavigation)?.openChat(chatId, ChatEntity.TASK_CHAT)
    }
}
