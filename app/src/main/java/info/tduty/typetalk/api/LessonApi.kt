package info.tduty.typetalk.api

import info.tduty.typetalk.data.dto.CreateDialogDTO
import info.tduty.typetalk.data.dto.LessonDTO
import info.tduty.typetalk.data.dto.TeacherLessonDTO
import info.tduty.typetalk.data.dto.TeacherTaskDTO
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
interface LessonApi {

    @GET("lessons")
    fun getLessons(@Header("Authorization") token: String): Observable<List<LessonDTO>>

    @GET("lessons/{lesson_id}")
    fun getLesson(
        @Header("Authorization") token: String,
        @Path("lesson_id") lessonId: String
    ): Observable<LessonDTO>

    @GET("lessons/teacher/{class_id}")
    fun getTeacherLessons(
        @Header("Authorization") token: String,
        @Path("class_id") classId: String
    ): Observable<List<TeacherLessonDTO>>

    @GET("lessons/tasks/teacher/{lesson_id}")
    fun getTeacherTasks(
        @Header("Authorization") token: String,
        @Path("lesson_id") lessonId: String,
        @Query("class_id") classId: String
    ): Observable<List<TeacherTaskDTO>>

    @POST("/lessons/create_dialogs")
    fun createDialogs(
        @Header("Authorization") token: String,
        @Body dto: CreateDialogDTO
    ): Completable
}