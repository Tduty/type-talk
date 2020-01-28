package info.tduty.typetalk.data.entity

import androidx.room.Embedded
import androidx.room.Relation

class AccountLessons {
    @Embedded
    lateinit var account: AccountEntity

    @Relation(
        parentColumn = "id",
        entityColumn = "account_id",
        entity = LessonEntity::class
    )
    lateinit var lessons: List<LessonEntity>
}