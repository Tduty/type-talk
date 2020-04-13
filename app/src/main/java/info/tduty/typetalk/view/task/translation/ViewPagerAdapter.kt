package info.tduty.typetalk.view.task.translation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.tduty.typetalk.data.model.TranslationVO
import java.util.*

class ViewPagerAdapter: RecyclerView.Adapter<PagerVH>() {

    private var translationList: List<TranslationVO> = Collections.emptyList()

    fun setupTranslationList(translationList: List<TranslationVO>) {
        this.translationList = translationList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH = PagerVH.newInstance(parent)

    override fun getItemCount(): Int = translationList.size

    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.onBind(translationList[position])
}