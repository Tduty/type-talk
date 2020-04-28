package info.tduty.typetalk.view.teacher.manage.lessons

import info.tduty.typetalk.data.model.LessonManageVO
import info.tduty.typetalk.domain.interactor.teacher.TeacherLessonInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class LessonsManagePresenter(
    private val view: LessonsManageView,
    private val teacherLessonInteractor: TeacherLessonInteractor) {

    private var loadDisposable: Disposable? = null
    private lateinit var classId: String

    fun onCreate(classId: String) {
        this.classId = classId
        loadLessons()
    }

    fun onDestroy() {
        loadDisposable?.dispose()
    }

    fun openLesson(lesson: LessonManageVO) {
        view.openManageTasks(classId, lesson)
    }

    private fun loadLessons() {
        loadDisposable?.dispose()
        loadDisposable = teacherLessonInteractor.loadAllLessons(classId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.setLessons(it)
            }, Timber::e)
    }
}
