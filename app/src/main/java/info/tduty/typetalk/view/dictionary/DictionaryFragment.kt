package info.tduty.typetalk.view.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.DictionaryVO
import info.tduty.typetalk.di.DaggerDictionaryComponent
import info.tduty.typetalk.di.DictionaryModule
import info.tduty.typetalk.view.dictionary.adapter.DictionaryListAdapter
import kotlinx.android.synthetic.main.fragment_dictionary.view.*
import javax.inject.Inject

class DictionaryFragment : Fragment(R.layout.fragment_dictionary), DictionaryView {

    init {
        DaggerDictionaryComponent.builder().dictionaryModule(DictionaryModule(this)).build().inject(this)
    }

    companion object {

        @JvmStatic
        fun newInstance() : DictionaryFragment {
            return DictionaryFragment()
        }
    }

    @Inject
    lateinit var presenter: DictionaryPresenter
    lateinit var adapter: DictionaryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.fragment_dictionary,
            container, false
        )
        setupRV(view)
        return view
    }

    private fun setupRV(view: View) {
        adapter = DictionaryListAdapter()
        view.rvDictionaryList.layoutManager = LinearLayoutManager(context)
        view.rvDictionaryList.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun setDictionary(dictionaryList: List<DictionaryVO>) {
        adapter.setDictionaryList(dictionaryList)
    }
}
