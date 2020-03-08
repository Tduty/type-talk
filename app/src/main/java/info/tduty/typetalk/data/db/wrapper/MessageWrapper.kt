package info.tduty.typetalk.data.db.wrapper

import info.tduty.typetalk.data.db.dao.MessageDao
import info.tduty.typetalk.data.db.model.MessageEntity
import io.reactivex.Completable

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
}
