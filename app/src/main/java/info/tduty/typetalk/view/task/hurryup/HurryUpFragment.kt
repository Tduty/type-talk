package info.tduty.typetalk.view.task.hurryup

import android.os.Bundle
import androidx.fragment.app.Fragment
import info.tduty.typetalk.R
import info.tduty.typetalk.data.model.TaskVO

class HurryUpFragment: Fragment(R.layout.fragment_task_hurry_up), HurryUpView {

    companion object {

        private const val ARGUMENT_TASK_VO = "task_vo"

        @JvmStatic
        fun newInstance(taskVO: TaskVO) : HurryUpFragment {
            val bundle = Bundle()
            bundle.putParcelable(ARGUMENT_TASK_VO, taskVO)
            val fragment = HurryUpFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}