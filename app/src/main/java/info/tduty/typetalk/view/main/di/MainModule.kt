package info.tduty.typetalk.view.main.di

import dagger.Module
import dagger.Provides
import info.tduty.typetalk.data.db.AppDatabase
import info.tduty.typetalk.data.pref.UserDataHelper
import info.tduty.typetalk.domain.interactor.LessonInteractor
import info.tduty.typetalk.domain.managers.DatabaseManager
import info.tduty.typetalk.domain.managers.EventManager
import info.tduty.typetalk.socket.SocketController
import info.tduty.typetalk.view.main.MainPresenter
import info.tduty.typetalk.view.main.MainView

@Module
class MainModule(val view: MainView) {

    @Provides
    fun provideView() : MainView {
        return view
    }

    @Provides
    fun provideMainPresenter(
        view: MainView,
        lessonInteractor: LessonInteractor,
        eventManager: EventManager,
        databaseManager: DatabaseManager,
        socketController: SocketController,
        userDataHelper: UserDataHelper,
        appDatabase: AppDatabase
    ): MainPresenter {
        return MainPresenter(
            view,
            lessonInteractor,
            eventManager,
            databaseManager,
            socketController,
            userDataHelper,
            appDatabase
        )
    }
}