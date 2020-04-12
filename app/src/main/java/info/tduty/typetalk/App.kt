package info.tduty.typetalk

import android.app.Application
import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import info.tduty.typetalk.di.AppComponent
import info.tduty.typetalk.di.AppModule
import info.tduty.typetalk.di.DaggerAppComponent
import info.tduty.typetalk.domain.managers.AppLifecycleEventManager
import info.tduty.typetalk.domain.managers.DataLoaderManager
import info.tduty.typetalk.socket.EventHandler
import info.tduty.typetalk.socket.SocketController
import javax.inject.Inject

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
class App : Application() {

    companion object {

        @JvmStatic
        fun get(context: Context): App {
            return context.applicationContext as App
        }
    }

    @Inject
    lateinit var appLifecycleEventManager: AppLifecycleEventManager
    @Inject
    lateinit var socketController: SocketController
    @Inject
    lateinit var eventHandler: EventHandler
    @Inject
    lateinit var dataLoaderManager: DataLoaderManager

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initAppComponent()
        initAppLifecycleObserver()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
            .also { it.inject(this) }
    }

    private fun initAppLifecycleObserver() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleEventManager)
    }
}