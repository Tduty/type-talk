package info.tduty.typetalk.domain.interactor

import info.tduty.typetalk.data.db.model.ChatEntity
import info.tduty.typetalk.data.db.wrapper.ChatWrapper
import info.tduty.typetalk.data.model.ChatVO
import info.tduty.typetalk.domain.mapper.ChatMapper
import info.tduty.typetalk.domain.provider.ChatProvider
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber

/**
 * Created by Evgeniy Mezentsev on 08.03.2020.
 */
class ChatInteractor(
    private val chatWrapper: ChatWrapper,
    private val chatProvider: ChatProvider,
    private val chatMapper: ChatMapper
) {

    fun getChat(chatId: String?, type: String): Observable<ChatVO> {
        return when (type) {
            ChatEntity.CLASS_CHAT -> getClassChat()
            ChatEntity.TEACHER_CHAT -> getTeacherChat()
            else -> chatId?.let { getChat(it) } ?: Observable.empty()
        }
    }

    fun getTeacherChat(): Observable<ChatVO> {
        return chatWrapper.getTeacherChat()
            .doOnError { Timber.e(it) }
            .map { chatMapper.toVO(it) }
            .switchIfEmpty(
                chatProvider.getChatTeacher()
                    .doOnError { Timber.e(it) }
                    .flatMap { dto ->
                        chatWrapper.insert(chatMapper.toDB(dto))
                            .andThen(Observable.just(dto))
                            .map { chatMapper.toVO(it) }
                    }
            )
    }

    fun loadChats(): Completable {
        return chatProvider.getChats()
            .flatMapCompletable { chats ->
                chatWrapper.insert(chats.map { chatMapper.toDB(it) })
            }
    }

    fun loadAllChats(): Observable<List<String>> {
        return chatProvider.getChats()
            .flatMap { chats ->
                chatWrapper.insert(chats.map { chatMapper.toDB(it) })
                    .andThen(Observable.just(chats.map { it.id }))
            }
    }

    private fun getChat(chatId: String): Observable<ChatVO> {
        return chatWrapper.getByChatId(chatId)
            .map { chatMapper.toVO(it) }
            .switchIfEmpty(
                chatProvider.getChat(chatId)
                    .doOnError { Timber.e(it) }
                    .flatMap { dto ->
                        chatWrapper.insert(chatMapper.toDB(dto))
                            .andThen(Observable.just(dto))
                            .map { chatMapper.toVO(it) }
                    }
            )
    }

    private fun getClassChat(): Observable<ChatVO> {
        return chatWrapper.getClassChat()
            .map { chatMapper.toVO(it) }
            .switchIfEmpty (
                chatProvider.getChatClass()
                    .doOnError { Timber.e(it) }
                    .flatMap { dto ->
                        chatWrapper.insert(chatMapper.toDB(dto))
                            .andThen(Observable.just(dto))
                            .map { chatMapper.toVO(it) }
                    }
            )
    }
}