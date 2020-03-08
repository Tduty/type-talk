package info.tduty.typetalk.view.chat.di

import dagger.Subcomponent
import info.tduty.typetalk.view.chat.ChatFragment

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
@Subcomponent(modules = [ChatModule::class])
interface ChatComponent {

    fun inject(view: ChatFragment)
}