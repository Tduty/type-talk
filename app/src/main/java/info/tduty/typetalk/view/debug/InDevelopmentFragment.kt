package info.tduty.typetalk.view.debug

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import info.tduty.typetalk.R
import info.tduty.typetalk.view.MainActivity
import info.tduty.typetalk.view.ViewNavigation
import kotlinx.android.synthetic.main.fragment_dictionary.view.*

/**
 * Created by Evgeniy Mezentsev on 07.04.2020.
 */
class InDevelopmentFragment : Fragment(R.layout.fragment_in_development) {

    companion object {

        @JvmStatic
        fun newInstance(): InDevelopmentFragment {
            return InDevelopmentFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.setupToolbar(
            view.toolbar as Toolbar,
            R.string.in_development_title,
            true
        )
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_chat -> {
                (activity as? ViewNavigation)?.openTeacherChat()
            }
            R.id.action_dictionary -> {
                (activity as? ViewNavigation)?.openDictionary()
            }
        }
        return true
    }

}
