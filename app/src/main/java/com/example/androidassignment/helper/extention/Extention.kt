package com.example.androidassignment.helper.extention

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.ColorStateList
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


/**
 * check json data is valid or not
 */
fun String.isJSONValid(): Boolean {
    try {
        JSONObject(this)
    } catch (ex: JSONException) {
        try {
            JSONArray(this)
        } catch (ex1: JSONException) {
            return false
        }
    }
    return true
}

/**
 * Color provider
 */
fun Context.provideColor(color: Int): Int {
    return ContextCompat.getColor(this, color)
}

/**
 * hide view
 */
fun View.hideView() {
    this.visibility = View.GONE
}

/**
 * show view
 */
fun View.showView() {
    this.visibility = View.VISIBLE
}

/**
 * hide keyboard
 */

fun Activity.hideKeyboard() {
    currentFocus?.let {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        it.clearFocus()
    }
}

/**
 * check parent activity reference
 */
fun View.getParentActivity(): AppCompatActivity?{
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

