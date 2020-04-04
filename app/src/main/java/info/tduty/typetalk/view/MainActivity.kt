package info.tduty.typetalk.view

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import info.tduty.typetalk.R
import info.tduty.typetalk.view.chat.ChatFragment
import info.tduty.typetalk.view.dictionary.DictionaryFragment
import info.tduty.typetalk.view.lesson.LessonFragment
import info.tduty.typetalk.view.main.MainFragment
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main), ViewNavigation {

    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //showFragment(LoginFragment.newInstance())
        showFragment(MainFragment.newInstance())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun openMain() {
        showFragment(MainFragment.newInstance())
    }

    override fun openChat(chatId: String) {
        showFragment(ChatFragment.newInstance(chatId))
    }

    override fun openLesson(lessonId: String) {
        showFragment(LessonFragment.newInstance(lessonId))
    }

    override fun openDictionary() {
        showFragment(DictionaryFragment.newInstance())
    }

    fun setupToolbar(toolbar: Toolbar, @StringRes title: Int, withBackButton: Boolean) {
        toolbar.setTitle(title)
        this.setSupportActionBar(toolbar)

        this.supportActionBar?.setHomeButtonEnabled(withBackButton)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(withBackButton)
    }

    private fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame, fragment)
        if (currentFragment != null) transaction.addToBackStack(null)
        currentFragment = fragment
        try {
            transaction.commit()
        } catch (ignored: IllegalStateException) {
            Timber.e(ignored)
        }
    }
}
