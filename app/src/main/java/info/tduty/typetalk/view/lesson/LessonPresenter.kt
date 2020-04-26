package info.tduty.typetalk.view.lesson

import info.tduty.typetalk.data.event.payload.LessonProgressPayload
import info.tduty.typetalk.data.model.LessonVO
import info.tduty.typetalk.data.model.TaskStateUpdated
import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.domain.interactor.LessonInteractor
import info.tduty.typetalk.domain.interactor.TaskInteractor
import info.tduty.typetalk.domain.managers.EventManager
import info.tduty.typetalk.socket.SocketController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 2019-11-30.
 */
class LessonPresenter(
    val view: LessonView,
    val taskInteractor: TaskInteractor,
    val lessonInteractor: LessonInteractor,
    val eventManager: EventManager
) {

    private val disposables = CompositeDisposable()
    private lateinit var lesson: LessonVO
    private lateinit var tasks: List<TaskVO>

    fun onCreate(lessonId: String) {
        setupLesson(lessonId)
        setupListener()
    }

    fun onDestroy() {
        disposables.dispose()
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
                    val task2 = mutableListOf<TaskVO>()
                    tasks.forEach {
                        task2.add(it)
                        task2.add(it)
                        task2.add(it)
                        task2.add(it)
                    }
                    view.setTasks(task2)
                    view.setToolbarTitle(lesson.title)
                }, Timber::e)
        )
    }

    fun setupListener() {
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
        view.openTask(tasks.first { it.id == id && it.type == task }, lesson.id)
    }

    private fun handleTaskUpdated(task: TaskStateUpdated) {
        val updatedTask = tasks.find { taskItem -> taskItem.id == task.taskId }
        updatedTask?.checked = task.isCompleted
        view.setTasks(tasks)
    }
}
