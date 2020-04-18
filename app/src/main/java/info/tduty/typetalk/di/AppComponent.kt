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
import info.tduty.typetalk.view.task.dictionarypicationary.di.DictionaryPictionaryComponent
import info.tduty.typetalk.view.task.dictionarypicationary.di.DictionaryPictionaryModule
import info.tduty.typetalk.view.task.flashcard.di.FlashcardComponent
import info.tduty.typetalk.view.task.flashcard.di.FlashcardModule
import info.tduty.typetalk.view.task.hurryup.di.HurryUpComponent
import info.tduty.typetalk.view.task.hurryup.di.HurryUpModule
import info.tduty.typetalk.view.task.phrasebuilding.di.PhraseBuildingComponent
import info.tduty.typetalk.view.task.phrasebuilding.di.PhraseBuildingModule
import info.tduty.typetalk.view.task.wordamess.di.WordamessComponent
import info.tduty.typetalk.view.task.wordamess.di.WordamessModule
import info.tduty.typetalk.view.task.translation.di.TranslationComponent
import info.tduty.typetalk.view.task.translation.di.TranslationModule
import info.tduty.typetalk.view.teacher.classinfo.di.ClassComponent
import info.tduty.typetalk.view.teacher.classinfo.di.ClassModule
import info.tduty.typetalk.view.teacher.main.di.MainTeacherComponent
import info.tduty.typetalk.view.teacher.main.di.MainTeacherModule
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

    fun plus(module: MainTeacherModule): MainTeacherComponent

    fun plus(module: ClassModule): ClassComponent

    fun plus(module: ChatModule): ChatComponent

    fun plus(module: LoginModule): LoginComponent

    fun plus(module: AuthQRModule): AuthQRComponent

    fun plus(module: FlashcardModule): FlashcardComponent

    fun plus(module: WordamessModule): WordamessComponent

    fun plus(module: PhraseBuildingModule): PhraseBuildingComponent

    fun plus(module: TranslationModule): TranslationComponent

    fun plus(module: DictionaryPictionaryModule): DictionaryPictionaryComponent

    fun plus(module: HurryUpModule): HurryUpComponent
}
