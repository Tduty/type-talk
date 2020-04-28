package info.tduty.typetalk.view.lesson

import info.tduty.typetalk.data.model.LessonVO
import info.tduty.typetalk.data.model.TaskStateUpdated
import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.domain.interactor.DialogTaskInteractor
import info.tduty.typetalk.domain.interactor.LessonInteractor
import info.tduty.typetalk.domain.interactor.TaskInteractor
import info.tduty.typetalk.domain.managers.EventManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */
class LessonPresenter(
    private val view: LessonView,
    private val taskInteractor: TaskInteractor,
    private val lessonInteractor: LessonInteractor,
    private val eventManager: EventManager,
    private val dialogTaskInteractor: DialogTaskInteractor
) {

    private val disposables = CompositeDisposable()
    private var openDialogDisposable: Disposable? = null
    private lateinit var lesson: LessonVO
    private lateinit var tasks: List<TaskVO>

    fun onCreate(lessonId: String) {
        setupLesson(lessonId)
        setupListener()
    }

    fun onDestroy() {
        disposables.dispose()
        openDialogDisposable?.dispose()
    }

    fun cancelOpenDialog() {
        openDialogDisposable?.dispose()
    }

    private fun setupLesson(lessonId: String) {
        disposables.add(
            lessonInteractor.getLesson(lessonId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ lesson ->
                    this.lesson = lesson
                    setupTasks(lesson)
                }, Timber::e)
        )
    }

    private fun setupTasks(lesson: LessonVO) {
        disposables.add(
            taskInteractor.getTasks(lesson.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ tasks ->
                    this.tasks = tasks
                    view.setTasks(tasks)
                    view.setToolbarTitle(lesson.title)
                }, Timber::e)
        )
    }

    private fun setupListener() {
        disposables.add(
            eventManager.taskStatusUpated()
                .filter { it.lessonId == lesson.id }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    handleTaskUpdated(it)
                }, Timber::e)
        )
    }

    fun openTask(id: String, task: TaskType) {
        val taskVO = tasks.first { it.id == id && it.type == task }
        when (taskVO.type) {
            TaskType.FLASHCARDS -> view.openFlashcardTask(taskVO)
            TaskType.WORDAMESS -> view.openWordamessTask(taskVO)
            TaskType.HURRY_UP -> view.openHurryUpTask(taskVO)
            TaskType.PHRASE_BUILDING -> view.openPhraseBuilderTask(taskVO)
            TaskType.TRANSLATION -> view.openTranslationTask(taskVO)
            TaskType.DICTIONARY_PICTIONARY -> view.openDictionaryPictionary(taskVO)
            TaskType.DIALOG_WITH_UNKNOWN -> openDialog(taskVO)
            TaskType.EMPTY -> {
            }
        }
    }

    private fun handleTaskUpdated(task: TaskStateUpdated) {
        val updatedTask = tasks.find { taskItem -> taskItem.id == task.taskId }
        updatedTask?.checked = task.isCompleted
        view.setTasks(tasks)
    }

    private fun openDialog(taskVO: TaskVO) {
        openDialogDisposable?.dispose()
        openDialogDisposable = dialogTaskInteractor.getTaskChat(taskVO.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isPresent) view.openDialogTask(it.get())
                else loadTaskChat(taskVO)
            }, Timber::e)
    }

    private fun loadTaskChat(taskVO: TaskVO) {
        openDialogDisposable?.dispose()
        openDialogDisposable = dialogTaskInteractor.loadTaskChat(
            taskVO.lessonId,
            taskVO.id,
            taskVO.payload
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showDialogSearchView() }
            .doFinally { view.hideDialogSearchView() }
            .subscribe({
                view.openDialogTask(it)
            }, Timber::e)
    }
}
