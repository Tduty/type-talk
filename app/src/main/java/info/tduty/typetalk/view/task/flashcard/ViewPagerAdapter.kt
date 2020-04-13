package info.tduty.typetalk.view.task.flashcard

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.FlashcardVO
import java.util.*

class ViewPagerAdapter : RecyclerView.Adapter<PagerVH>() {

    private var flashcardVO: List<FlashcardVO> = Collections.emptyList()

    fun setupFlashCardVO(flashcardVO: List<FlashcardVO>) {
        this.flashcardVO = flashcardVO
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH = PagerVH.newInstance(parent)

    override fun getItemCount(): Int = flashcardVO.size

    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.onBind(flashcardVO[position])
}