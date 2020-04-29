package info.tduty.typetalk.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.db.model.ChatEntity
import info.tduty.typetalk.data.model.DialogVO
import info.tduty.typetalk.data.model.LessonManageVO
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.data.pref.UserDataHelper
import info.tduty.typetalk.view.base.BaseFragment
import info.tduty.typetalk.view.chat.ChatFragment
import info.tduty.typetalk.view.chat.ChatStarter
import info.tduty.typetalk.view.debug.InDevelopmentFragment
import info.tduty.typetalk.view.dictionary.DictionaryFragment
import info.tduty.typetalk.view.lesson.LessonFragment
import info.tduty.typetalk.view.login.password.LoginFragment
import info.tduty.typetalk.view.login.password.qr.AuthQRFragment
import info.tduty.typetalk.view.main.MainFragment
import info.tduty.typetalk.view.task.dictionarypicationary.DictionaryPictionaryFragment
import info.tduty.typetalk.view.task.flashcard.FlashcardFragment
import info.tduty.typetalk.view.task.hurryup.HurryUpFragment
import info.tduty.typetalk.view.task.phrasebuilding.PhraseBuildingFragment
import info.tduty.typetalk.view.task.translation.TranslationFragment
import info.tduty.typetalk.view.task.wordamess.WordamessFragment
import info.tduty.typetalk.view.teacher.classinfo.ClassFragment
import info.tduty.typetalk.view.teacher.main.MainTeacherFragment
import info.tduty.typetalk.view.teacher.manage.dialog.choose.ChooseStudentsFragment
import info.tduty.typetalk.view.teacher.manage.dialog.list.DialogsFragment
import info.tduty.typetalk.view.teacher.manage.lessons.LessonsManageFragment
import info.tduty.typetalk.view.teacher.manage.tasks.TasksManageFragment
import kotlinx.android.synthetic.main.alert_dialog_information.view.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), ViewNavigation {

    @Inject
    lateinit var userDataHelper: UserDataHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupComponent()

        if (userDataHelper.isSavedUser()) showMainFragment()
        else showFragment(LoginFragment.newInstance())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_chat -> {
                openTeacherChat()
                true
            }
            R.id.action_dictionary -> {
                openDictionary()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        when (supportFragmentManager.findFragmentById(R.id.content_frame)) {
            is AuthQRFragment -> openLoginAuth()
            else -> super.onBackPressed()
        }
    }

    private fun showMainFragment() {
        if (userDataHelper.getSavedUser().isTeacher) showFragment(MainTeacherFragment.newInstance())
        else showFragment(MainFragment.newInstance())
    }

    override fun closeFragment() {
        supportFragmentManager.popBackStack()
    }

    override fun openMain() {
        showMainFragment()
    }

    override fun openClass(classId: String, className: String) {
        showFragment(ClassFragment.newInstance(classId, className))
    }

    override fun openManageLessons(classId: String) {
        showFragment(LessonsManageFragment.newInstance(classId))
    }

    override fun openManageTasks(classId: String, lesson: LessonManageVO) {
        showFragment(TasksManageFragment.newInstance(classId, lesson))
    }

    override fun openDialogs(dialogs: List<DialogVO>) {
        showFragment(DialogsFragment.newInstance(dialogs))
    }

    override fun openChooseStudentForDialog(classId: String, lessonId: String, taskId: String) {
        showFragment(ChooseStudentsFragment.newInstance(classId, lessonId, taskId))
    }

    override fun openChat(chatId: String, chatType: String?) {
        showFragment(ChatFragment.newInstance(chatId, chatType))
    }

    override fun openChat(chatStarter: ChatStarter) {
        showFragment(ChatFragment.newInstance(chatStarter))
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
        showFragment(LessonFragment.newInstance(lessonId), lessonId)
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

    override fun openTranslationTask(taskVO: TaskVO) {
        showFragment(TranslationFragment.newInstance(taskVO))
    }

    override fun openDictionaryPictionary(taskVO: TaskVO) {
        showFragment(DictionaryPictionaryFragment.newInstance(taskVO))
    }

    override fun openWordamessTask(taskVO: TaskVO) {
        showFragment(WordamessFragment.newInstance(taskVO))
    }

    override fun openPhaserBuilderTask(taskVO: TaskVO) {
        showFragment(PhraseBuildingFragment.newInstance(taskVO))
    }

    override fun openHurryUpTask(taskVO: TaskVO) {
        showFragment(HurryUpFragment.newInstance(taskVO))
    }

    override fun showAppInfoDialog() {
        if (userDataHelper.isTeacher()) return
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog_information, null)

        mDialogView.tv_title.text = resources.getString(R.string.welcome_app)
        mDialogView.tv_message.text = resources.getString(R.string.welcome_info)
        mDialogView.btn_first_button.visibility = View.VISIBLE
        mDialogView.btn_second_button.visibility = View.GONE
        mDialogView.btn_first_button.text = getString(R.string.close)

        val mAlertDialog = AlertDialog.Builder(this).setView(mDialogView).show()
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        mDialogView.btn_first_button.setOnClickListener { mAlertDialog.dismiss() }
    }

    fun setupToolbar(toolbar: Toolbar, @StringRes title: Int, withBackButton: Boolean) {
        toolbar.setTitle(title)
        setupToolbar(toolbar, withBackButton)
    }

    fun setupToolbar(toolbar: Toolbar, title: String, withBackButton: Boolean) {
        toolbar.title = title
        setupToolbar(toolbar, withBackButton)
    }

    fun setupToolbar(toolbar: Toolbar, withBackButton: Boolean) {
        this.setSupportActionBar(toolbar)

        this.supportActionBar?.setHomeButtonEnabled(withBackButton)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(withBackButton)
    }

    private fun setupComponent() {
        App.get(this)
            .appComponent
            .inject(this)
    }

    private fun showFragment(fragment: Fragment, tag: String? = null) {
        val transaction = supportFragmentManager.beginTransaction()
        val currentFragment = supportFragmentManager.findFragmentById(R.id.content_frame)
        if (currentFragment?.javaClass == fragment.javaClass) return
        setupAnimation(fragment)
        transaction.add(R.id.content_frame, fragment, tag)
        if (!isFragmentWithoutBackStack(currentFragment)) transaction.addToBackStack(null)
        try {
            transaction.commit()
        } catch (ignored: IllegalStateException) {
            Timber.e(ignored)
        }
    }

    private fun setupAnimation(fragment: Fragment) {
        val enterFade = Slide(Gravity.END)
        enterFade.startDelay = 80
        enterFade.duration = 200
        fragment.enterTransition = enterFade
    }

    private fun isFragmentWithoutBackStack(fragment: Fragment?): Boolean {
        return if (fragment == null) true
        else when (fragment) {
            is AuthQRFragment,
            is LoginFragment -> true
            else -> false
        }
    }
}
