package info.tduty.typetalk.view.chat

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.MessageVO
import info.tduty.typetalk.view.chat.di.ChatModule
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_dictionary.view.*
import kotlinx.android.synthetic.main.toolbar_chat.*
import javax.inject.Inject


class ChatFragment : Fragment(R.layout.fragment_chat), ChatView {

    companion object {

        private const val ARGUMENT_CHAT_ID = "chat_id"

        @JvmStatic
        fun newInstance(chatId: String): ChatFragment {
            val bundle = Bundle()
            bundle.putString(ARGUMENT_CHAT_ID, chatId)
            val fragment = ChatFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var adapter: ChatRvAdapter
    @Inject
    lateinit var presenter: ChatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chatId = arguments?.getString(ARGUMENT_CHAT_ID)
            ?: throw IllegalArgumentException("chatId is null")

        setupToolbar()
        setupRv()
        setupListeners()

        presenter.onCreate(chatId)
    }

    override fun onDetach() {
        super.onDetach()
        presenter.onDestroy()
    }

    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(ChatModule(this))
            .inject(this)
    }

    private fun setupRv() {
        adapter = ChatRvAdapter()
        rv_messages.layoutManager = LinearLayoutManager(context)
        rv_messages.adapter = adapter
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
        tv_toolbar_title.title.text = title
    }

    override fun setToolbarIcon(icon: String) {
        //TODO добавить отображение icon
    }

    override fun addEvent(messageVO: MessageVO) {
        adapter.addEvent(messageVO)
    }

    override fun addEvents(messageVO: List<MessageVO>) {
        messageVO.forEach { adapter.addEvent(it) }
    }

    override fun setEvents(messageVO: List<MessageVO>) {
        adapter.setEvents(messageVO)
    }
}
