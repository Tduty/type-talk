package info.tduty.typetalk.view.chat

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.db.model.ChatEntity
import info.tduty.typetalk.data.model.MessageVO
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.chat.di.ChatModule
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.toolbar_chat.*
import javax.inject.Inject


class ChatFragment : Fragment(R.layout.fragment_chat), ChatView {

    companion object {

        private const val ARGUMENT_CHAT_ID = "chat_id"
        private const val ARGUMENT_CHAT_TYPE = "chat_type"

        @JvmStatic
        fun newInstance(chatId: String? = null, chatType: String? = null): ChatFragment {
            val bundle = Bundle()
            bundle.putString(ARGUMENT_CHAT_ID, chatId)
            bundle.putString(ARGUMENT_CHAT_TYPE, chatType ?: ChatEntity.CHAT)
            val fragment = ChatFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var menu: Menu
    private lateinit var adapter: ChatRvAdapter
    private lateinit var layoutManager: LinearLayoutManager
    @Inject
    lateinit var presenter: ChatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chatId = arguments?.getString(ARGUMENT_CHAT_ID)
        val type = arguments?.getString(ARGUMENT_CHAT_TYPE) ?: ChatEntity.CHAT

        setupToolbar()
        setupRv()
        setupListeners()
        setHasOptionsMenu(true)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        presenter.onCreate(chatId, type)
    }

    override fun onDetach() {
        super.onDetach()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        presenter.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        this.menu = menu
        inflater.inflate(R.menu.menu_main, menu)
        menu.findItem(R.id.action_chat)?.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_chat -> (activity as? ViewNavigation)?.openTeacherChat()
            R.id.action_dictionary -> (activity as? ViewNavigation)?.openDictionary()
        }
        return true
    }


    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(ChatModule(this))
            .inject(this)
    }

    private fun setupRv() {
        adapter = ChatRvAdapter()
        rv_messages.adapter = adapter
        rv_messages.addOnLayoutChangeListener { v, _, _, _, b, _, _, _, oldB ->
            if (b < oldB) { v.post { scrollToBottom() } }
        }
    }

    private fun setupListeners() {
        iv_send.setOnClickListener {
            presenter.sendMessage(et_message.text?.toString() ?: "")
            et_message.text = SpannableStringBuilder("")
        }
    }

    private fun setupToolbar() {
        iv_back_button.setOnClickListener { activity?.onBackPressed() }
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar_chat as Toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun setToolbarTitle(title: String) {
        tv_toolbar_title.text = title
    }

    override fun setToolbarIcon(icon: Int) {
        iv_toolbar_logo.setImageResource(icon)
    }

    override fun showTeacherMenu() {
        menu.findItem(R.id.action_chat)?.isVisible = true
    }

    override fun addEvent(messageVO: MessageVO) {
        adapter.addEvent(messageVO)
        scrollToBottom()
    }

    override fun addEvents(messageVO: List<MessageVO>) {
        messageVO.forEach { adapter.addEvent(it) }
    }

    override fun setEvents(messageVO: List<MessageVO>) {
        adapter.setEvents(messageVO)
    }

    private fun scrollToBottom() {
        rv_messages.layoutManager?.scrollToPosition(adapter.itemCount - 1)
    }
}
