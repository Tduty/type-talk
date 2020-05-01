package info.tduty.typetalk.domain.interactor.teacher

import info.tduty.typetalk.data.db.wrapper.ChatWrapper
import info.tduty.typetalk.data.dto.ChatDTO
import info.tduty.typetalk.data.model.DialogVO
import info.tduty.typetalk.domain.interactor.HistoryInteractor
import info.tduty.typetalk.domain.mapper.ChatMapper
import info.tduty.typetalk.domain.provider.ChatProvider
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Evgeniy Mezentsev on 25.04.2020.
 */
class TeacherDialogInteractor(
    private val chatProvider: ChatProvider,
    private val chatWrapper: ChatWrapper,
    private val chatMapper: ChatMapper,
    private val historyInteractor: HistoryInteractor
) {

    fun loadTaskChat(lessonId: String, taskId: String): Single<List<DialogVO>> {
        return chatProvider.getTaskChat(lessonId, taskId)
            .flatMap { chats ->
                chatWrapper.insert(chats.map { chatMapper.toDB(it) })
                    .andThen(Observable.fromIterable(chats))
            }
            .flatMap { chat ->
                historyInteractor.loadHistory(chat.id)
                    .ignoreElements()
                    .andThen(Observable.just(dtoToVO(chat)))
            }
            .toList()
    }

    private fun dtoToVO(dto: ChatDTO): DialogVO {
        return DialogVO(dto.id, dto.title)
    }
}
