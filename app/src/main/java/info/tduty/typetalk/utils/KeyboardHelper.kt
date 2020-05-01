package info.tduty.typetalk.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyboardHelper {

    companion object {
        fun hideKeyboard(activity: Activity?, screenView: View? = null) {
            val view = screenView ?: activity?.window?.currentFocus
            val imm =  activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

            imm?.let {
                view?.let {
                    imm.hideSoftInputFromWindow(view.windowToken,0)
                }
            }
        }
    }
}
