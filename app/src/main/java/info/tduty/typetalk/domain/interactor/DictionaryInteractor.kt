package info.tduty.typetalk.domain.interactor

import info.tduty.typetalk.data.db.model.DictionaryEntity
import info.tduty.typetalk.data.db.model.LessonEntity
import info.tduty.typetalk.data.db.wrapper.DictionaryWrapper
import info.tduty.typetalk.data.db.wrapper.LessonWrapper
import info.tduty.typetalk.data.dto.DictionaryDTO
import info.tduty.typetalk.data.model.DictionaryVO
import info.tduty.typetalk.data.model.VocabularyVO
import info.tduty.typetalk.domain.provider.DictionaryProvider
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 09.04.2020.
 */
class DictionaryInteractor(
    private val dictionaryWrapper: DictionaryWrapper,
    private val lessonWrapper: LessonWrapper,
    private val dictionaryProvider: DictionaryProvider
) {

    fun loadDictionary(): Completable {
        return dictionaryProvider.getDictionary()
            .doOnError { Timber.e(it) }
            .flatMapCompletable { dtoList ->
                dictionaryWrapper.insert(dtoList.map { toDB(it) })
            }
    }

    fun loadDictionary(lessonId: String): Completable {
        return dictionaryProvider.getDictionary(lessonId)
            .doOnError { Timber.e(it) }
            .flatMapCompletable { dtoList ->
                dictionaryWrapper.insert(dtoList.map { toDB(it) })
            }
    }

    fun getDictionary(): Observable<List<DictionaryVO>> {
        return dictionaryWrapper.getAll()
            .flatMapSingle { dictionaries ->
                val dictionariesMap = dictionaries.groupBy({ it.lessonsId }, { it })
                lessonWrapper.getLessons(dictionaries.map { it.lessonsId })
                    .flatMap { Observable.fromIterable(it) }
                    .map { toVO(it, dictionariesMap[it.lessonId] ?: emptyList()) }
                    .filter { it.vocabularies.isNotEmpty() }
                    .toList()
            }
    }

    private fun toDB(dictionaryDTO: DictionaryDTO): DictionaryEntity {
        return DictionaryEntity(
            dictionaryId = dictionaryDTO.id,
            word = dictionaryDTO.phrase,
            translation = dictionaryDTO.translation,
            transcription = dictionaryDTO.transcription,
            lessonsId = dictionaryDTO.lessonId
        )
    }

    private fun toVO(lesson: LessonEntity, dictionaries: List<DictionaryEntity>): DictionaryVO {
        return DictionaryVO(
            lesson.title,
            dictionaries.map { toVocabularyVO(it) }
        )
    }

    private fun toVocabularyVO(dictionary: DictionaryEntity): VocabularyVO {
        return VocabularyVO(
            dictionary.word,
            dictionary.transcription,
            dictionary.translation
        )
    }
}