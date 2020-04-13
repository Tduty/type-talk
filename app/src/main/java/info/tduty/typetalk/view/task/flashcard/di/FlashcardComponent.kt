package info.tduty.typetalk.view.task.flashcard.di

import dagger.Subcomponent
import info.tduty.typetalk.view.task.flashcard.FlashcardFragment
import info.tduty.typetalk.view.task.flashcard.di.FlashcardModule

@Subcomponent(modules = [FlashcardModule::class])
interface FlashcardComponent {

    fun inject(view: FlashcardFragment)
}
