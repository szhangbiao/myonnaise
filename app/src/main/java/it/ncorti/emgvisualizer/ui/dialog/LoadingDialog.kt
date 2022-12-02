package it.ncorti.emgvisualizer.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import it.ncorti.emgvisualizer.R

class LoadingDialog : Dialog {

    constructor(context: Context) : super(context)

    constructor(context: Context, theme: Int) : super(context, theme)

    fun showDialog(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) {
        return showDialog(context, null, cancelable, cancelListener)
    }

    private var dialog: LoadingDialog? = null
    fun showDialog(context: Context, message: CharSequence?, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) {
        if (dialog == null) {
            dialog = LoadingDialog(context, R.style.LoadingDialog)
            dialog?.setContentView(R.layout.view_loading_dialog)
            dialog?.setCanceledOnTouchOutside(false)
            dialog?.setCancelable(cancelable)
            dialog?.setOnCancelListener(cancelListener)
            dialog?.window?.attributes?.gravity = Gravity.CENTER
            val lp = dialog?.window?.attributes
            lp?.dimAmount = 0.2f
            dialog?.window?.attributes = lp
            dialog?.show()
        } else if (dialog?.isShowing == false) {
            dialog?.show()
        }
    }

    fun dismissDialog() {
        if (dialog != null && dialog?.isShowing == true) {
            dialog?.dismiss()
        }
    }
}
