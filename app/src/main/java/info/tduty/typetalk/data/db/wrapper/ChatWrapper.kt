package info.tduty.typetalk.data.db.wrapper

import info.tduty.typetalk.data.db.dao.ChatDao
import info.tduty.typetalk.data.db.model.ChatEntity
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 08.03.2020.
 */
class ChatWrapper(private val chatDao: ChatDao) {

    fun insert(chat: ChatEntity): Completable {
        return chatDao.insert(chat)
    }

    fun insert(chats: List<ChatEntity>): Completable {
        return chatDao.insertList(chats)
    }

    fun getByChatId(chatId: String): Observable<ChatEntity> {
        return chatDao.getChatBy(chatId)
            .toObservable()
    }
}