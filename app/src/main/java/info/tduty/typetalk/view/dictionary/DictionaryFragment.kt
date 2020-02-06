package info.tduty.typetalk.view.dictionary

import android.os.Bundle
import androidx.fragment.app.Fragment
import info.tduty.typetalk.R

class DictionaryFragment : Fragment(R.layout.fragment_dictionary) {

    companion object {

        @JvmStatic
        fun newInstance() =
            DictionaryFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}