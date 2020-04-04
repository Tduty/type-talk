package info.tduty.typetalk.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import info.tduty.typetalk.R
import info.tduty.typetalk.view.chat.ChatFragment
import info.tduty.typetalk.view.dictionary.DictionaryFragment
import info.tduty.typetalk.view.lesson.LessonFragment
import info.tduty.typetalk.view.login.password.LoginFragment
import info.tduty.typetalk.view.main.MainFragment
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main), ViewNavigation {

    companion object {
        private const val LOGIN_PASSWORD = "LOGIN_PASSWORD"
        private const val LOGIN_QR = "LOGIN_QR"
        private const val MAIN = "MAIN"
        private const val CHAT = "CHAT"
        private const val LESSON = "LESSON"
        private const val DICTIONARY = "DICTIONARY"
    }

    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showFragment(LOGIN_PASSWORD, LoginFragment.newInstance())
        //showFragment(MAIN, MainFragment.newInstance())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        when (currentFragment) {
            is ChatFragment -> showFragment(MAIN, MainFragment.newInstance())
            else -> super.onBackPressed()
        }
    }

    override fun openMain() {
        showFragment(MAIN, MainFragment.newInstance())
    }

    override fun openChat(chatId: String) {
        showFragment(CHAT, ChatFragment.newInstance(chatId))
    }

    override fun openLesson(lessonId: String) {
        showFragment(LESSON, LessonFragment.newInstance(lessonId))
    }

    override fun openDictionary() {
        showFragment(DICTIONARY, DictionaryFragment.newInstance())
    }

    fun setupToolbar(toolbar: Toolbar, @StringRes title: Int, withBackButton: Boolean) {
        toolbar.setTitle(title)
        this.setSupportActionBar(toolbar)
        this.supportActionBar?.setHomeButtonEnabled(withBackButton)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(withBackButton)
    }

    private fun showFragment(tag: String, fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        currentFragment?.let {
            if (it.isVisible) transaction.hide(it)
        }
        var newFragment = supportFragmentManager.findFragmentByTag(tag)
        if (newFragment == null) {
            newFragment = fragment
            transaction.add(R.id.content_frame, newFragment, tag)
        } else {
            newFragment.arguments = fragment.arguments
        }
        currentFragment = newFragment
        transaction.show(newFragment)
        try {
            transaction.commitNow()
        } catch (ignored: IllegalStateException) {
            Timber.e(ignored)
        }
    }

}
