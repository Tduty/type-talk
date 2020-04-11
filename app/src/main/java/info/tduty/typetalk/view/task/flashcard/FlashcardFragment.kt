package info.tduty.typetalk.view.task.flashcard

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.widget.ViewPager2
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.FlashcardVO
import info.tduty.typetalk.view.MainActivity
import info.tduty.typetalk.view.ViewNavigation
import info.tduty.typetalk.view.task.flashcard.di.FlashcardModule
import kotlinx.android.synthetic.main.fragment_dictionary.view.*
import kotlinx.android.synthetic.main.fragment_task_flashcard.*
import javax.inject.Inject


class FlashcardFragment: Fragment(R.layout.fragment_task_flashcard), FlashcardView {

    companion object {

        private const val ARGUMENT_LESSON_ID = "lesson_id"

        @JvmStatic
        fun newInstance(lessonsId: String): FlashcardFragment {
            val bundle = Bundle()
            bundle.putString(ARGUMENT_LESSON_ID, lessonsId)
            val fragment = FlashcardFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var presenter: FlashcardPresenter
    lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lessonId = arguments?.getString(ARGUMENT_LESSON_ID)
            ?: throw IllegalArgumentException("lesson id is null")

        setupToolbar(view.toolbar as Toolbar, R.string.task_screen_default_title, true)
        setHasOptionsMenu(true)

        setupViewPager()
        setupListener()

        presenter.onCreate(lessonId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_dictionary -> (activity as? ViewNavigation)?.openDictionary()
            R.id.action_chat -> (activity as? ViewNavigation)?.openTeacherChat()
        }
        return true
    }

    private fun setupToolbar(toolbar: Toolbar, @StringRes title: Int, withBackButton: Boolean) {
        toolbar.setTitle(title)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setHomeButtonEnabled(withBackButton)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(withBackButton)
    }

    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(FlashcardModule(this))
            .inject(this)
    }

    private fun setupListener() {
        btn_next?.setOnClickListener {
            presenter.onClickNext(vp_flashcard.currentItem)
        }

        vp_flashcard.registerOnPageChangeCallback( object: ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                btn_next.isEnabled = position != (vp_flashcard.adapter?.itemCount ?: 0) - 1
            }
        })
    }

    private fun setupViewPager() {
        adapter = ViewPagerAdapter()
        vp_flashcard.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun setupFlashcards(flashcardVO: List<FlashcardVO>) {
        adapter.setupFlashCardVO(flashcardVO)
        adapter.notifyDataSetChanged()
    }

    override fun showWord(position: Int, isAnimated: Boolean) {
        vp_flashcard.adapter.let {
            if (position >= 0 && position < vp_flashcard.adapter!!.itemCount)
                vp_flashcard.setCurrentItem(position, isAnimated)
        }
    }
}