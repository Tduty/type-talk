package info.tduty.typetalk.domain.interactor

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import info.tduty.typetalk.data.db.model.ChatEntity.Companion.TASK_CHAT
import info.tduty.typetalk.data.db.model.MessageEntity
import info.tduty.typetalk.data.db.wrapper.ChatWrapper
import info.tduty.typetalk.data.db.wrapper.MessageWrapper
import info.tduty.typetalk.data.db.wrapper.TaskWrapper
import info.tduty.typetalk.domain.mapper.ChatMapper
import info.tduty.typetalk.domain.provider.ChatProvider
import info.tduty.typetalk.utils.Optional
import info.tduty.typetalk.view.chat.ChatStarter
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 23.04.2020.
 */
class DialogTaskInteractor(
    private val gson: Gson,
    private val chatWrapper: ChatWrapper,
    private val chatProvider: ChatProvider,
    private val chatMapper: ChatMapper,
    private val messageWrapper: MessageWrapper,
    private val historyInteractor: HistoryInteractor,
    private val taskWrapper: TaskWrapper
) {

    fun getTaskChat(taskId: String): Observable<Optional<ChatStarter>> {
        return taskWrapper.getTasksById(taskId)
            .flatMap {
                if (it.isPresent) {
                    val dialog = gson.fromJson(it.get().payload, DialogTask::class.java)
                    if (dialog.chatId != null) {
                        messageWrapper.getByChatId(dialog.chatId)
                            .flatMap { messages ->
                                Observable.just(Optional.of(toChatStarter(dialog, messages)))
                            }
                    } else Observable.just(Optional.empty())
                } else Observable.error(IllegalArgumentException("task not found"))
            }
    }

    fun loadTaskChat(lessonId: String, taskId: String, payload: String): Observable<ChatStarter> {
        return chatProvider.getTaskChat(lessonId, taskId)
            .flatMap { chats ->
                chatWrapper.insert(chats.map { chatMapper.toDB(it) })
                    .andThen(updateDialogTask(chats[0].id, taskId, payload))
            }
            .flatMap {  dialog ->
                if (dialog.chatId != null) {
                    historyInteractor.loadHistory(dialog.chatId)
                        .flatMap { Observable.just(toChatStarter(dialog, it)) }
                } else Observable.error(NullPointerException("chatId is null"))
            }
    }

    private fun updateDialogTask(chatId: String, taskId: String, payload: String): Observable<DialogTask> {
        val dialog = gson.fromJson(payload, DialogTask::class.java)
        val updateDialog = DialogTask(chatId, dialog.description, dialog.countMessages)
        return taskWrapper.updatePayload(taskId, gson.toJson(updateDialog))
            .andThen(Observable.just(updateDialog))
    }

    private fun toChatStarter(dialog: DialogTask, messages: List<MessageEntity>): ChatStarter {
        return ChatStarter(
            dialog.chatId,
            TASK_CHAT,
            dialog.description,
            messages.filter { it.isMy }.size < dialog.countMessages,
            dialog.countMessages
        )
    }

    data class DialogTask(
        @SerializedName("chat_id") val chatId: String?,
        @SerializedName("description") val description: String,
        @SerializedName("count_message") val countMessages: Int
    )
}
