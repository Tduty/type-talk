package info.tduty.typetalk.view.chat

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.*
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.db.model.ChatEntity
import info.tduty.typetalk.data.model.CorrectionVO
import info.tduty.typetalk.data.model.MessageVO
import info.tduty.typetalk.extenstion.dpToPx
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.base.BaseFragment
import info.tduty.typetalk.view.chat.di.ChatModule
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.toolbar_chat.*
import javax.inject.Inject


class ChatFragment : BaseFragment(R.layout.fragment_chat), ChatView {

    companion object {

        private const val ARGUMENT_CHAT_STARTER = "chat_starter"

        @JvmStatic
        fun newInstance(chatId: String? = null, chatType: String? = null): ChatFragment {
            val bundle = Bundle()
            bundle.putParcelable(
                ARGUMENT_CHAT_STARTER,
                ChatStarter(chatId, chatType ?: ChatEntity.CHAT)
            )
            val fragment = ChatFragment()
            fragment.arguments = bundle
            return fragment
        }

        @JvmStatic
        fun newInstance(chatStarter: ChatStarter): ChatFragment {
            val bundle = Bundle()
            bundle.putParcelable(ARGUMENT_CHAT_STARTER, chatStarter)
            val fragment = ChatFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var menu: Menu
    private lateinit var adapter: ChatRvAdapter
    private var messageCountsAnim: Animator? = null
    private var isMessageCountsStarted = false

    @Inject
    lateinit var presenter: ChatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chatStarter = arguments?.getParcelable<ChatStarter>(ARGUMENT_CHAT_STARTER)
            ?: throw IllegalArgumentException("chat starter not found")

        setupToolbar()
        setupRv()
        setupListeners()
        setHasOptionsMenu(true)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        presenter.onCreate(chatStarter)
    }

    override fun onDetach() {
        super.onDetach()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        messageCountsAnim?.cancel()
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
        adapter = ChatRvAdapter { presenter.onMessageClick(it) }
        rv_messages.adapter = adapter
        rv_messages.addOnLayoutChangeListener { v, _, _, _, b, _, _, _, oldB ->
            if (b < oldB) { v.post { scrollToBottom() } }
        }
    }

    private fun setupListeners() {
        iv_send.setOnClickListener {
            presenter.onSendBtnClick(et_message.text?.toString() ?: "")
            et_message.text = SpannableStringBuilder("")
        }
        et_message.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                presenter.onChangeEditText(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
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

    override fun hideUserInput() {
        cl_input.visibility = View.GONE
    }

    override fun clearUserInput() {
        et_message.text.clear()
    }

    override fun showTeacherMenu() {
        menu.findItem(R.id.action_chat)?.isVisible = true
    }

    override fun addEventToStart(messageVO: MessageVO) {
        adapter.addEventToStart(messageVO)
    }

    override fun addEvent(messageVO: MessageVO) {
        adapter.addEvent(messageVO)
        scrollToBottom()
    }

    override fun addEvents(messages: List<MessageVO>) {
        adapter.addEvents(messages)
        scrollToBottom()
    }

    override fun setEvents(messages: List<MessageVO>) {
        adapter.setEvents(messages)
    }

    override fun updateCorrection(correctionVO: CorrectionVO) {
        adapter.updateCorrection(correctionVO)
    }

    override fun showCorrectionState(
        title: String,
        body: String,
        type: CorrectionVO.AdditionalType
    ) {
        ll_correct_block.visibility = View.VISIBLE
        tv_correction_title.text = title
        tv_correction_body.text = body
        if (type == CorrectionVO.AdditionalType.CORRECTION) {
            et_message.text = SpannableStringBuilder(body)
        }
        iv_cancel_correction.setOnClickListener { presenter.cancelCorrection() }
    }

    override fun hideCorrectionState() {
        ll_correct_block.visibility = View.GONE
        tv_correction_title.text = null
        tv_correction_body.text = null
        et_message.text = null
    }

    override fun showMessageActionDialog(messageVO: MessageVO) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.chat_message_action)
            .setItems(R.array.message_action) { _, which ->
                when (which) {
                    0 -> presenter.onCorrectMessage(messageVO)
                    1 -> presenter.onCommentMessage(messageVO)
                }
            }
        builder.create().show()
    }

    override fun showCountMessages(count: Int, foldingAnimate: Boolean) {
        ll_count_messages.visibility = View.VISIBLE
        tv_count_message.text = count.toString()
        if (foldingAnimate) {
            ll_count_messages.post {
                startFoldingAnimationCountMessages()
            }
        }
    }

    override fun showErrorAboutRussianSymbols() {
        Toast.makeText(requireContext(), R.string.chat_input_russian_symbols_error, Toast.LENGTH_SHORT).show()
    }

    private fun startFoldingAnimationCountMessages() {
        if (isMessageCountsStarted || ll_count_messages.visibility == View.GONE) return
        isMessageCountsStarted = true
        messageCountsAnim = AnimatorSet().apply {
            duration = 500
            startDelay = 3000
            interpolator = LinearInterpolator()
            val startTranslation = 0.0F.dpToPx()
            val endTranslation = -(tv_message_count_description.width + 15.0F.dpToPx())
            val translationAnimator = ValueAnimator.ofFloat(startTranslation, endTranslation)
            translationAnimator.addUpdateListener { animation ->
                ll_count_messages?.translationX = animation.animatedValue as Float
            }
            val alphaAnimator = ValueAnimator.ofFloat(1.0F, 0.5F)
            alphaAnimator.addUpdateListener { animation ->
                ll_count_messages?.alpha = animation.animatedValue as Float
            }
            playTogether(translationAnimator, alphaAnimator)
        }
        messageCountsAnim?.start()
    }

    private fun scrollToBottom() {
        rv_messages.layoutManager?.scrollToPosition(adapter.itemCount - 1)
    }
}
