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
import info.tduty.typetalk.domain.AppLifecycleEventManager
import info.tduty.typetalk.socket.SocketController
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
    fun provideUserDataHelper(context: Context, gson: Gson): UserDataHelper {
        return UserDataSharedPreferencesHelper(context, gson)
    }

    @Provides
    @Singleton
    fun provideSocketController(
        context: Context,
        gson: Gson,
        userDataHelper: UserDataHelper,
        appLifecycleEventManager: AppLifecycleEventManager
    ): SocketController {
        return SocketController(context, gson, userDataHelper, appLifecycleEventManager)
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
