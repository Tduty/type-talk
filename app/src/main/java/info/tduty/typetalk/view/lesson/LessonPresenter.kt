package info.tduty.typetalk.view.lesson

import info.tduty.typetalk.data.model.LessonVO
import info.tduty.typetalk.data.model.TaskType
import info.tduty.typetalk.data.model.TaskVO
import info.tduty.typetalk.domain.interactor.LessonInteractor
import info.tduty.typetalk.domain.interactor.TaskInteractor
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
    val lessonInteractor: LessonInteractor
) {

    private val disposables = CompositeDisposable()
    private lateinit var lesson: LessonVO
    private lateinit var tasks: List<TaskVO>

    fun onCreate(lessonId: String) {
        setupLesson(lessonId)
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

    fun onDestroy() {
        disposables.dispose()
    }

    fun openTask(id: String, task: TaskType) {
        // TODO open fragment for task
    }
}
