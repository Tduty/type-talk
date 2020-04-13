package info.tduty.typetalk.view.task.phrasebuilding

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.PhraseBuildingVO
import java.util.*

/**
 * Created by Evgeniy Mezentsev on 12.04.2020.
 */
class VpAdapter : RecyclerView.Adapter<PagerVH>() {

    lateinit var presenter: PhraseBuilderItemPresenter
    private var phrases: List<PhraseBuildingVO> = Collections.emptyList()

    fun setPhrases(phrases: List<PhraseBuildingVO>) {
        this.phrases = phrases
        presenter.setPhrases(phrases)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
        return PagerVH.newInstance(parent, presenter)
    }

    override fun getItemCount() = phrases.size

    override fun onBindViewHolder(holder: PagerVH, position: Int) {
        holder.onBind(phrases[position], position == phrases.lastIndex)
    }

    override fun onViewAttachedToWindow(holder: PagerVH) {
        super.onViewAttachedToWindow(holder)
        presenter.onViewAttachedToWindow(holder.phrases.id, holder)
    }

    override fun onViewDetachedFromWindow(holder: PagerVH) {
        super.onViewDetachedFromWindow(holder)
        presenter.onViewDetachedFromWindow(holder.phrases.id)
    }
}
