package info.tduty.typetalk.view.task.phrasebuilding

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.PhraseBuildingVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.utils.alert.AlertDialogItems
import info.tduty.typetalk.utils.alert.AlertDialogItemsVO
import info.tduty.typetalk.utils.alert.TypeAlertItem
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.task.phrasebuilding.di.PhraseBuildingModule
import kotlinx.android.synthetic.main.fragment_dictionary.view.*
import kotlinx.android.synthetic.main.fragment_phrase_building.*
import javax.inject.Inject


/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
class PhraseBuildingFragment : Fragment(R.layout.fragment_phrase_building), PhraseBuildingView {

    companion object {

        private const val ARGUMENT_TASK_VO = "task_vo"

        @JvmStatic
        fun newInstance(taskVO: TaskVO): PhraseBuildingFragment {
            val bundle = Bundle()
            bundle.putParcelable(ARGUMENT_TASK_VO, taskVO)
            val fragment = PhraseBuildingFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var TITLE_FINISHED_ALERT: String
    private lateinit var TITLE_FAILED_ALERT: String
    private lateinit var BTN_COMPLETED_ALERT: String
    private lateinit var BTN_TRY_AGAIN_ALERT: String

    @Inject
    lateinit var presenter: PhraseBuildingPresenter
    lateinit var adapter: VpAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskVO = arguments?.getParcelable<TaskVO>(ARGUMENT_TASK_VO)
            ?: throw IllegalArgumentException("task is null")

        setupToolbar(view.toolbar as Toolbar, taskVO.title, true)
        setHasOptionsMenu(true)

        setupViewPager()
        setupTranslation()

        presenter.onCreate(taskVO)
    }

    private fun setupTranslation() {
        TITLE_FINISHED_ALERT =
            requireContext().resources.getString(R.string.alert_title_finished_task)
        TITLE_FAILED_ALERT = requireContext().resources.getString(R.string.alert_title_failed_task)
        BTN_COMPLETED_ALERT = requireContext().resources.getString(R.string.alert_btn_completed)
        BTN_TRY_AGAIN_ALERT = requireContext().resources.getString(R.string.alert_btn_try_again)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun setupViewPager() {
        adapter = VpAdapter()
        adapter.presenter = PhraseBuilderItemPresenter(presenter)
        vp_phrases.adapter = adapter
        vp_phrases.isUserInputEnabled = false
    }

    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(PhraseBuildingModule(this))
            .inject(this)
    }

    private fun setupToolbar(toolbar: Toolbar, title: String, withBackButton: Boolean) {
        toolbar.title = title
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setHomeButtonEnabled(withBackButton)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(withBackButton)
    }

    override fun setupPhrases(phrases: List<PhraseBuildingVO>) {
        adapter.setPhrases(phrases)
    }

    override fun nextPage() {
        if (vp_phrases.currentItem == adapter.itemCount - 1) presenter.completeTask()
        else vp_phrases.currentItem += 1
    }

    override fun nextPage(position: Int) {
        vp_phrases.currentItem = position
    }

    override fun completeTask() {
        (activity as? ViewNavigation)?.closeFragment()
    }

    override fun successCompletedWithIncorrectWord(incorrect: List<PhraseBuildingVO>) {
        val alert = AlertDialogItems(
            requireContext(),
            getPayloadForAlert(incorrect),
            false,
            TITLE_FINISHED_ALERT,
            BTN_COMPLETED_ALERT,
            null
        )

        alert.setListenerFirstButton {
            completeTask()
            alert.dismiss()
        }

        alert.showAlert()
    }

    override fun unsuccessComplete(incorrect: List<PhraseBuildingVO>) {
        val alert = AlertDialogItems(
            requireContext(),
            getPayloadForAlert(incorrect),
            true,
            TITLE_FAILED_ALERT,
            BTN_TRY_AGAIN_ALERT,
            BTN_COMPLETED_ALERT
        )

        alert.setListenerFirstButton {
            presenter.tryAgain()
            alert.dismiss()
        }

        alert.setListenerSecondButton {
            completeTask()
            alert.dismiss()
        }

        alert.showAlert()
    }

    private fun getPayloadForAlert(dpVO: List<PhraseBuildingVO>): List<AlertDialogItemsVO> {
        return dpVO.map {
            val type = TypeAlertItem.ERROR
            val topWord = getTopWord(it.phrases)
            AlertDialogItemsVO(
                topWord,
                it.buildingText,
                type
            )
        }
    }

    private fun getTopWord(words: List<String>): String {
        return words.joinToString(prefix = "(", postfix = ")")
    }
}