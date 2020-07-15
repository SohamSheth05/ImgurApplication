package com.example.imgur.utils


import android.app.Dialog
import android.content.Context
import android.view.Window
import com.example.imgur.R
import com.example.imgur.avi.AVLoadingIndicatorView
import com.example.imgur.common.Common

import java.util.*

class AlertUtils {
    internal var callback: AlertUtilsListener? = null

    interface AlertUtilsListener {
    }

    companion object {
        var dialog: Dialog? = null
        private lateinit var rotateLoading: AVLoadingIndicatorView

        private val max = 28
        private val min = 0
        fun showCustomProgressDialog(con: Context) {
            // create a Dialog component

            //  DebugLog.e("this is called from home ");

            try {
                val random = Random()
                random.nextInt(max - min)
                dialog = Dialog(con)
                dialog!!.setCancelable(false)
                // this line removes title
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                //tell the Dialog to use the dialog.xml as it's layout description
                dialog!!.setContentView(R.layout.loading_custom_taxiapp)
                rotateLoading = dialog!!.findViewById(R.id.rotateloading)

                rotateLoading.setIndicator(Common.INDICATORS[18])
                dialog!!.show()
                rotateLoading.show()
            } catch (e: Exception) {
                e.printStackTrace()
                dismissDialog()
            }

        }// end of showCustomProgressDialog

        fun dismissDialog() {
            try {
                if (dialog != null) {
                    dialog!!.dismiss()
                    rotateLoading.hide()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }
}
