package info.tduty.typetalk.view.task.phrasebuilding

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.PhraseBuildingVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.utils.alert.AlertDialogItems
import info.tduty.typetalk.utils.alert.AlertDialogItemsVO
import info.tduty.typetalk.utils.alert.TypeAlertItem
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.base.BaseFragment
import info.tduty.typetalk.view.task.phrasebuilding.di.PhraseBuildingModule
import kotlinx.android.synthetic.main.fragment_dictionary.view.*
import kotlinx.android.synthetic.main.fragment_phrase_building.*
import javax.inject.Inject


/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
class PhraseBuildingFragment : BaseFragment(R.layout.fragment_phrase_building), PhraseBuildingView {

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

        presenter.onCreate(taskVO)
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
        val alert = AlertDialogItems(requireContext())
            .title(R.string.alert_title_failed_task)
            .cancelable(true)
            .canceledOnTouchOutside(true)
            .items(getPayloadForAlert(incorrect))
            .firstButtonTitle(R.string.alert_btn_try_again)

        alert.setListenerFirstButton {
            presenter.sendEventCompleteTask()
            completeTask()
            alert.dismiss()
        }

        alert.showAlert()
    }

    override fun unsuccessComplete(incorrect: List<PhraseBuildingVO>) {
        val alert = AlertDialogItems(requireContext())
            .title(R.string.alert_title_failed_task)
            .cancelable(true)
            .canceledOnTouchOutside(true)
            .items(getPayloadForAlert(incorrect))
            .firstButtonTitle(R.string.alert_btn_try_again)
            .secondButtonTitle(R.string.alert_btn_completed)

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
            AlertDialogItemsVO(
                getTopWord(it.phrases),
                it.buildingText,
                TypeAlertItem.ERROR
            )
        }
    }

    private fun getTopWord(words: List<String>): String {
        return words.joinToString(prefix = "(", postfix = ")")
    }
}