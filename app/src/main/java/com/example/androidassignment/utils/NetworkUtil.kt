package com.example.androidassignment.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object NetworkUtil {

    fun isNetConnected(context: Context): Boolean {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.allNetworkInfo
        if (info != null)
            for (anInfo in info)
                if (anInfo.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
        return false
    }
}