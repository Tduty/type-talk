package info.tduty.typetalk.view.dictionary

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.App
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.DictionaryVO
import info.tduty.typetalk.view.MainActivity
import info.tduty.typetalk.view.dictionary.adapter.DictionaryListAdapter
import info.tduty.typetalk.view.dictionary.di.DictionaryModule
import kotlinx.android.synthetic.main.fragment_dictionary.*
import kotlinx.android.synthetic.main.fragment_dictionary.view.*
import javax.inject.Inject

class DictionaryFragment : Fragment(R.layout.fragment_dictionary), DictionaryView {

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
        setupFragmentComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.setupToolbar(view.toolbar as Toolbar, R.string.title_dictionary, true)

        setupRV(view)
        presenter.onCreate()
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

    override fun showEmptyPlaceholder() {
        ll_dictionary_empty.visibility = View.VISIBLE
    }

    override fun hideEmptyPlaceholder() {
        ll_dictionary_empty.visibility = View.GONE
    }

    override fun setDictionary(dictionaryList: List<DictionaryVO>) {
        adapter.setDictionaryList(dictionaryList)
    }

    private fun setupFragmentComponent() {
        App.get(this.requireContext())
            .appComponent
            .plus(DictionaryModule(this))
            .inject(this)
    }
}
