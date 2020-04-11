package info.tduty.typetalk.view.task.flashcard.di

import dagger.Subcomponent
import info.tduty.typetalk.view.task.flashcard.FlashcardFragment

@Subcomponent(modules = [FlashcardModule::class])
interface FlashcardComponent {

    fun inject(view: FlashcardFragment)
}