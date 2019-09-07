package com.example.androidassignment.rest

interface OnApiCallInteraction {
    //  100 > network not found  : 404 > server error.
    fun <T> onSuccess(body: T?)

    fun onFail(error: Int)
}