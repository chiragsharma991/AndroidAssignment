@file:Suppress("DEPRECATION")

package com.example.androidassignment.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.androidassignment.dashboard.view.MainActivity



object NetworkUtil {

    /**
     * Check the network condition
     * @return : true if connected / false if not connected
     */

    fun isNetConnected(context: Context): Boolean {

        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.allNetworkInfo
            for (anInfo in info)
                if (anInfo.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
        return false
    }

}