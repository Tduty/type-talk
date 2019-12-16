package info.tduty.typetalk.view.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import info.tduty.typetalk.R

class ChatFragment : Fragment(R.layout.fragment_chat) {

    companion object {

        @JvmStatic
        fun newInstance() = ChatFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
