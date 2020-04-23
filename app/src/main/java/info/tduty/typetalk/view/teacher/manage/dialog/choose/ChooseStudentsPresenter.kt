package info.tduty.typetalk.view.teacher.manage.dialog.choose

import info.tduty.typetalk.data.model.ChooseStudentVO
import info.tduty.typetalk.domain.interactor.StudentInteractor
import info.tduty.typetalk.domain.interactor.teacher.TeacherLessonInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class ChooseStudentsPresenter(
    private val view: ChooseStudentsView,
    private val studentInteractor: StudentInteractor,
    private val lessonInteractor: TeacherLessonInteractor
) {

    private lateinit var classId: String
    private lateinit var lessonId: String
    private lateinit var taskId: String

    private var loadDisposable: Disposable? = null
    private var createDialogsDisposable: Disposable? = null
    private lateinit var students: List<ChooseStudentVO>
    private val studentChecked = hashSetOf<String>()

    fun onCreate(classId: String, lessonId: String, taskId: String) {
        this.classId = classId
        this.lessonId = lessonId
        this.taskId = taskId
        loadStudents(classId)
    }

    fun onDestroy() {
        loadDisposable?.dispose()
    }

    fun clickOnStudent(studentId: String) {
        if (studentChecked.contains(studentId)) studentChecked.remove(studentId)
        else studentChecked.add(studentId)
    }

    fun createDialogs() {
        createDialogs(lessonId, taskId)
    }

    fun selectAll() {
        students.forEach { it.isChecked = true }
        studentChecked.addAll(students.map { it.id })
        view.setStudents(students)
    }

    private fun loadStudents(classId: String) {
        loadDisposable?.dispose()
        loadDisposable = studentInteractor.getStudents(classId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ students ->
                this.students = students.map { ChooseStudentVO.map(it) }
                view.setStudents(this.students)
            }, Timber::e)
    }

    private fun createDialogs(lessonId: String, taskId: String) {
        createDialogsDisposable?.dispose()
        createDialogsDisposable =
            lessonInteractor.createDialogs(lessonId, taskId, studentChecked.toList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.closeView()
                }, Timber::e)
    }
}
