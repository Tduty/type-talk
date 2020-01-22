package info.tduty.typetalk.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AccountLessons(
    @Embedded val account: AccountEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "account_id",
        entity = LessonEntity::class
    ) val lessons: List<LessonEntity>
)