package info.tduty.typetalk.view.teacher.manage.tasks

import info.tduty.typetalk.data.model.LessonManageVO
import info.tduty.typetalk.data.model.TaskManageVO
import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.domain.interactor.teacher.TeacherDialogInteractor
import info.tduty.typetalk.domain.interactor.teacher.TeacherLessonInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class TasksManagePresenter(
    private val view: TasksManageView,
    private val teacherLessonInteractor: TeacherLessonInteractor,
    private val dialogInteractor: TeacherDialogInteractor
) {

    private lateinit var classId: String
    private var loadDisposable: Disposable? = null
    private var loadDialogDisposable: Disposable? = null

    fun onCreate(classId: String, lesson: LessonManageVO) {
        this.classId = classId
        setupInfoAboutLesson(lesson)
        loadTasks(classId, lesson.id)
    }

    fun onDestroy() {
        loadDisposable?.dispose()
    }

    fun manageTask(task: TaskManageVO) {
        if (task.type == TaskType.DIALOG_WITH_UNKNOWN) loadDialogs(task.lessonId, task.id)
    }

    fun cancelOpenDialogs() {
        loadDialogDisposable?.dispose()
    }

    private fun setupInfoAboutLesson(lesson: LessonManageVO) {
        view.setupLessonIcon(lesson.icon)
        view.setupLessonTitle(lesson.name)
        view.setupLessonCompleted(lesson.studentsCount, lesson.completedCount)
    }

    private fun loadTasks(classId: String, lessonId: String) {
        loadDisposable?.dispose()
        loadDisposable = teacherLessonInteractor.loadAllTasks(classId, lessonId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.setTasks(it)
            }, Timber::e)
    }

    private fun loadDialogs(lessonId: String, taskId: String) {
        loadDialogDisposable?.dispose()
        loadDialogDisposable = dialogInteractor.loadTaskChat(lessonId, taskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showSearchView() }
            .doFinally { view.hideSearchView() }
            .subscribe({ dialogs ->
                if (dialogs.isNotEmpty()) view.openDialogs(dialogs)
                else view.openChooseStudentsForDialogs(classId, lessonId, taskId)
            }, Timber::e)
    }
}
