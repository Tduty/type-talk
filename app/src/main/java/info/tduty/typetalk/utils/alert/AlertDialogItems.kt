package info.tduty.typetalk.utils.alert

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import info.tduty.typetalk.R
import kotlinx.android.synthetic.main.alert_dialog_items.view.*

class AlertDialogItems(
    val context: Context
) {

    private var mBuilder: AlertDialog.Builder?
    private var mDialogView: View? =
        LayoutInflater.from(context).inflate(R.layout.alert_dialog_items, null)
    private var mAlertDialog: AlertDialog? = null

    private var alertDialogItemsVO: List<AlertDialogItemsVO> = emptyList()
    private var isTwoButton: Boolean = false
    @StringRes
    private var title: Int = -1
    @StringRes
    private var firstButtonTitle: Int = -1
    @StringRes
    private var secondButtonTitle: Int? = null

    init {
        mBuilder = AlertDialog.Builder(context).setView(mDialogView)
        mAlertDialog = mBuilder?.create()

        mDialogView?.btn_first_button?.setOnClickListener {
            mAlertDialog?.dismiss()
        }
        mDialogView?.btn_second_button?.setOnClickListener {
            mAlertDialog?.dismiss()
        }
    }

    fun items(items: List<AlertDialogItemsVO>): AlertDialogItems {
        this.alertDialogItemsVO = items
        return this
    }

    fun title(@StringRes title: Int): AlertDialogItems {
        this.title = title
        return this
    }

    fun firstButtonTitle(@StringRes firstButtonTitle: Int): AlertDialogItems {
        this.firstButtonTitle = firstButtonTitle
        return this
    }

    fun secondButtonTitle(@StringRes secondButtonTitle: Int): AlertDialogItems {
        this.isTwoButton = true
        this.secondButtonTitle = secondButtonTitle
        return this
    }

    fun cancelable(isCancelable: Boolean): AlertDialogItems {
        mAlertDialog?.setCancelable(isCancelable)
        return this
    }

    fun canceledOnTouchOutside(cancel: Boolean): AlertDialogItems {
        mAlertDialog?.setCanceledOnTouchOutside(cancel)
        return this
    }

    fun showAlert() {
        mAlertDialog?.let {
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            if (title != -1) mDialogView?.tv_title?.setText(title) else mDialogView?.tv_title?.text = ""
            if (firstButtonTitle != -1) mDialogView?.btn_first_button?.setText(firstButtonTitle) else mDialogView?.btn_first_button?.text = ""

            setupRV()

            if (isTwoButton) {
                mDialogView?.btn_second_button?.visibility = View.VISIBLE
                secondButtonTitle?.let { id ->
                    mDialogView?.btn_second_button?.setText(id)
                }
            } else {
                mDialogView?.btn_second_button?.visibility = View.GONE
            }
        }
        mAlertDialog?.show()
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

    private fun setupRV() {
        val adapter = AlertDialogItemsRVAdapter()
        adapter.setAlertDialogItemsVO(alertDialogItemsVO)
        mDialogView?.rv_information?.adapter = adapter
        mDialogView?.rv_information?.layoutManager = LinearLayoutManager(context)
    }
}