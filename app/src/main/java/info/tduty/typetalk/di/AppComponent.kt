package info.tduty.typetalk.di

import dagger.Component
import info.tduty.typetalk.App
import info.tduty.typetalk.view.MainActivity
import info.tduty.typetalk.view.chat.di.ChatComponent
import info.tduty.typetalk.view.chat.di.ChatModule
import info.tduty.typetalk.view.dictionary.di.DictionaryComponent
import info.tduty.typetalk.view.dictionary.di.DictionaryModule
import info.tduty.typetalk.view.lesson.di.LessonsComponent
import info.tduty.typetalk.view.lesson.di.LessonsModule
import info.tduty.typetalk.view.login.password.di.LoginComponent
import info.tduty.typetalk.view.login.password.di.LoginModule
import info.tduty.typetalk.view.login.password.qr.di.AuthQRComponent
import info.tduty.typetalk.view.login.password.qr.di.AuthQRModule
import info.tduty.typetalk.view.main.di.MainComponent
import info.tduty.typetalk.view.main.di.MainModule
import info.tduty.typetalk.view.task.flashcard.di.FlashcardComponent
import info.tduty.typetalk.view.task.flashcard.di.FlashcardModule
import javax.inject.Singleton

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        DatabaseModule::class,
        WrapperModule::class,
        ProviderModule::class,
        InteractorModule::class,
        MapperModule::class
    ]
)
interface AppComponent {

    fun inject(app: App)

    fun inject(activity: MainActivity)

    fun plus(module: DictionaryModule): DictionaryComponent

    fun plus(module: LessonsModule): LessonsComponent

    fun plus(module: MainModule): MainComponent

    fun plus(module: ChatModule): ChatComponent

    fun plus(module: LoginModule): LoginComponent

    fun plus(module: AuthQRModule): AuthQRComponent

    fun plus(module: FlashcardModule): FlashcardComponent

}
