package com.example.androidassignment.network

/**
 * handle multiple error and response both
 */
class LiveDataWrapper<T> {
    /**
     * pair first is the message and second(Int) is the code of the reponse
     */
    var errorMessage: Pair<String, Int>? = null
    var data: T? = null
}