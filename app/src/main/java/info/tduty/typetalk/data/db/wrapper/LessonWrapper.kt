package info.tduty.typetalk.data.db.wrapper

import info.tduty.typetalk.data.db.dao.LessonDao
import info.tduty.typetalk.data.db.model.LessonEntity
import io.reactivex.Completable

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
class LessonWrapper(private val lessonDao: LessonDao) {

    fun insert(lesson: LessonEntity): Completable {
        return lessonDao.insert(lesson)
    }

    fun insert(lessons: List<LessonEntity>): Completable {
        return lessonDao.insert(lessons)
    }
}