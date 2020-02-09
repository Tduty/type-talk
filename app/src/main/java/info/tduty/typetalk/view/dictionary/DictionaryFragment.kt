package info.tduty.typetalk.view.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.DictionaryVO
import info.tduty.typetalk.view.dictionary.adapter.DictionaryListAdapter
import kotlinx.android.synthetic.main.fragment_dictionary.view.*

class DictionaryFragment : Fragment(R.layout.fragment_dictionary), DictionaryView {

    companion object {
        private lateinit var presenter: DictionaryPresenter
        private lateinit var adapter: DictionaryListAdapter

        @JvmStatic
        fun newInstance() : DictionaryFragment {
            presenter = DictionaryPresenter()
            return DictionaryFragment()
        }
    }

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
            container, true
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
