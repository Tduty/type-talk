package info.tduty.typetalk.utils.alert

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.R
import kotlinx.android.synthetic.main.alert_dialog_items.view.*

class AlertDialogItems(
    val context: Context,
    val alertDialogItemsVO: List<AlertDialogItemsVO>,
    private val isTwoButton: Boolean,
    val title: String,
    private val firstButtonTitle: String,
    private val secondButtonTitle: String?
) {

    private var mBuilder: AlertDialog.Builder?
    private var mDialogView: View? = LayoutInflater.from(context).inflate(R.layout.alert_dialog_items, null)
    private var mAlertDialog: AlertDialog? = null

    init {
        mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
    }

    fun showAlert() {
        mAlertDialog = mBuilder?.show()
        mAlertDialog?.let {
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val titleString = title

            mDialogView?.tv_title?.text = titleString

            mDialogView?.btn_first_button?.text = firstButtonTitle

            val adapter = AlertDialogItemsRVAdapter()
            adapter.setAlertDialogItemsVO(alertDialogItemsVO)
            mDialogView?.rv_information?.adapter = adapter
            mDialogView?.rv_information?.layoutManager = LinearLayoutManager(context)

            if (isTwoButton) {
                mDialogView?.btn_second_button?.visibility = View.VISIBLE
                mDialogView?.btn_second_button?.text = secondButtonTitle
            } else {
                mDialogView?.btn_second_button?.visibility = View.GONE
            }
        }
    }

    fun dismiss() {
        mAlertDialog?.dismiss()
    }

    fun setListenerFirstButton(listener: (view: View) -> Unit) {
        mDialogView?.btn_first_button?.setOnClickListener(listener)
    }

    fun setListenerSecondButton(listener: (view: View) -> Unit) {
        mDialogView?.btn_second_button?.setOnClickListener(listener)
    }
}