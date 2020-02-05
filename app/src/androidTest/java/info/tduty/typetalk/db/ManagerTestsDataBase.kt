package info.tduty.typetalk.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ChatDataTests::class,
    MessageDataTests::class,
    LessonsDataTests::class,
    TaskDataTests::class,
    DictionaryDataTests::class
)
class ManagerTestsDataBase
