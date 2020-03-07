package info.tduty.typetalk.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import info.tduty.typetalk.data.event.Event
import info.tduty.typetalk.data.pref.UserDataHelper
import info.tduty.typetalk.data.pref.UserDataSharedPreferencesHelper
import info.tduty.typetalk.data.serializer.EventDeserializer
import info.tduty.typetalk.domain.interactor.HistoryInteractor
import info.tduty.typetalk.domain.interactor.LessonInteractor
import info.tduty.typetalk.domain.managers.AppLifecycleEventManager
import info.tduty.typetalk.domain.managers.EventManager
import info.tduty.typetalk.socket.EventBusRx
import info.tduty.typetalk.socket.EventHandler
import info.tduty.typetalk.socket.SocketController
import info.tduty.typetalk.socket.SocketEventListener
import javax.inject.Singleton

/**
 * Created by Evgeniy Mezentsev on 05.03.2020.
 */
@Module
class AppModule(private val application: Application) {

    private var BASIC_GSON: Gson? = null

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideAppLifecycleEventManager(): AppLifecycleEventManager {
        return AppLifecycleEventManager()
    }

    @Provides
    @Singleton
    fun provideEventManager(): EventManager {
        return EventManager()
    }

    @Provides
    @Singleton
    fun provideUserDataHelper(context: Context, gson: Gson): UserDataHelper {
        return UserDataSharedPreferencesHelper(context, gson)
    }

    @Provides
    @Singleton
    fun provideSocketController(
        context: Context,
        gson: Gson,
        userDataHelper: UserDataHelper,
        appLifecycleEventManager: AppLifecycleEventManager,
        socketEventListener: SocketEventListener
    ): SocketController {
        return SocketController(context, gson, userDataHelper, appLifecycleEventManager,
            socketEventListener)
    }

    @Provides
    @Singleton
    fun provideSocketEventListener(): SocketEventListener {
        return SocketEventListener(EventBusRx())
    }

    @Provides
    @Singleton
    fun provideEventHandler(socketEventListener: SocketEventListener,
                            historyInteractor: HistoryInteractor,
                            lessonInteractor: LessonInteractor
    ): EventHandler {
        return EventHandler(socketEventListener, historyInteractor, lessonInteractor)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val basicGson: Gson = provideBasicGson() ?: GsonBuilder().create()
        return GsonBuilder()
            .registerTypeAdapter(Event::class.java, EventDeserializer(basicGson))
            .create()
    }

    private fun provideBasicGson(): Gson? {
        return if (BASIC_GSON == null) {
            BASIC_GSON = GsonBuilder()
                .create()
            BASIC_GSON
        } else BASIC_GSON
    }
}
