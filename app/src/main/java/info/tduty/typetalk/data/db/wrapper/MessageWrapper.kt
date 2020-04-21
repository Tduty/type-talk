package info.tduty.typetalk.data.db.wrapper

import info.tduty.typetalk.data.db.dao.MessageDao
import info.tduty.typetalk.data.db.model.MessageEntity
import info.tduty.typetalk.data.model.MessageVO
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
class MessageWrapper(private val messageDao: MessageDao) {

    fun insert(message: MessageEntity): Completable {
        return messageDao.insert(message)
    }

    fun insert(messages: List<MessageEntity>): Completable {
        return messageDao.insert(messages)
    }

    fun updateAdditional(syncId: String, type: Int, text: String): Completable {
        return messageDao.updateAdditional(syncId, type, text)
    }

    fun getByChatId(chatId: String): Observable<List<MessageEntity>> {
        return messageDao.getByChatId(chatId)
            .toObservable()
    }
}
