package com.example.androidassignment.utils

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.example.androidassignment.R
import com.example.androidassignment.helper.adapter.extention.provideColor
import com.google.android.material.snackbar.Snackbar

object SnackbarUtil {

    fun errorSnackbar(message: String, view: View?, listener : OnSnackbarDismiss? = null) {
        val snackbar = Snackbar.make(view!!, message, Snackbar.LENGTH_LONG)
        val snackbarView: View = snackbar.view
        val textView: TextView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        snackbarView.setBackgroundColor(view.context.provideColor(R.color.colorAccent))
        textView.setTextColor(Color.WHITE)
        snackbar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                listener?.onDismissSnackbar()
            }
        })
        snackbar.show()
    }

    fun successSnackbar(message: String, view: View?, listener : OnSnackbarDismiss? = null) {
        val snackbar = Snackbar.make(view!!, message, Snackbar.LENGTH_SHORT)
        val snackbarView: View = snackbar.view
        val textView: TextView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        snackbarView.setBackgroundColor(view.context.provideColor(R.color.colorAccent))
        textView.setTextColor(Color.WHITE)
        snackbar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                listener?.onDismissSnackbar()
            }
        })
        snackbar.show()
    }

    interface OnSnackbarDismiss{
        fun onDismissSnackbar()
    }
}
