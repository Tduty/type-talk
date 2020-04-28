package info.tduty.typetalk.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import info.tduty.typetalk.R

/**
 * Created by Evgeniy Mezentsev on 27.04.2020.
 */
abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        view?.setBackgroundColor(resources.getColor(R.color.alabaster))
        view?.setOnTouchListener { _, _ -> true }
        return view
    }
}
