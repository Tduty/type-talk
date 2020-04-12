package info.tduty.typetalk.view

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.db.model.ChatEntity
import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.data.pref.UserDataHelper
import info.tduty.typetalk.view.chat.ChatFragment
import info.tduty.typetalk.view.debug.InDevelopmentFragment
import info.tduty.typetalk.view.dictionary.DictionaryFragment
import info.tduty.typetalk.view.lesson.LessonFragment
import info.tduty.typetalk.view.login.password.LoginFragment
import info.tduty.typetalk.view.login.password.qr.AuthQRFragment
import info.tduty.typetalk.view.main.MainFragment
import info.tduty.typetalk.view.task.flashcard.FlashcardFragment
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), ViewNavigation {

    private var currentFragment: Fragment? = null

    @Inject
    lateinit var userDataHelper: UserDataHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupComponent()

        if (userDataHelper.isSavedUser()) showFragment(MainFragment.newInstance())
        else showFragment(LoginFragment.newInstance())
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

    override fun openTeacherChat() {
        showFragment(ChatFragment.newInstance(chatType = ChatEntity.TEACHER_CHAT))
    }

    override fun openClassChat() {
        showFragment(ChatFragment.newInstance(chatType = ChatEntity.CLASS_CHAT))
    }

    override fun openBots() {
        showFragment(InDevelopmentFragment.newInstance())
    }

    override fun openLesson(lessonId: String) {
        showFragment(LessonFragment.newInstance(lessonId))
    }

    override fun openDictionary() {
        showFragment(DictionaryFragment.newInstance())
    }

    override fun openQRAuth() {
        showFragment(AuthQRFragment.newInstance())
    }

    override fun openLoginAuth() {
        showFragment(LoginFragment.newInstance())
    }

    override fun openFlashcardTask(taskVO: TaskVO) {
        showFragment(FlashcardFragment.newInstance(taskVO))
    }

    fun setupToolbar(toolbar: Toolbar, @StringRes title: Int, withBackButton: Boolean) {
        toolbar.setTitle(title)
        this.setSupportActionBar(toolbar)

        this.supportActionBar?.setHomeButtonEnabled(withBackButton)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(withBackButton)
    }

    private fun setupComponent() {
        App.get(this)
            .appComponent
            .inject(this)
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
