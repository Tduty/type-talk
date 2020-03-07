package info.tduty.typetalk.di

import dagger.Component
import info.tduty.typetalk.App
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
        InteractorModule::class
    ]
)
interface AppComponent {

    fun inject(app: App)

    fun plus(module: DictionaryModule): DictionaryComponent

    fun plus(module: LessonsModule): LessonsComponent

    fun plus(module: MainModule): MainComponent
}
