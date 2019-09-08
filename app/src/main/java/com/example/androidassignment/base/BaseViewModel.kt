package com.example.androidassignment.base


import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.androidassignment.R
import com.example.androidassignment.network.APIServices
import com.example.androidassignment.network.ApiRetrofit
import com.example.androidassignment.network.LiveDataWrapper
import com.example.androidassignment.utils.NetworkUtil
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseViewModel(application: Application) : AndroidViewModel(application){

    var webservice: APIServices? = ApiRetrofit.retrofitInstance?.create(APIServices::class.java)

    /**
     * @param loadingVisibility showing progressbar (true/false) once api call
     */
    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    fun <T> callAPI(call: Call<T>) : MutableLiveData<LiveDataWrapper<T>>{

        val data = MutableLiveData<LiveDataWrapper<T>>()
        val dataWrapper = LiveDataWrapper<T>()
        if (isInternetAvailable()) {
            /**
             * Show loader
             */
            onLoaderVisible()

            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    /**
                     * Hide loader
                     */
                    onHideLoader()

                    try {
                        if (response.isSuccessful) {
                            dataWrapper.data = response.body()
                        } else {
                            val mErrorBody: String = response.errorBody()!!.string()
                            dataWrapper.errorMessage = Pair(mErrorBody, response.code())
                        }
                        data.value = dataWrapper
                    } catch (e: Exception) {
                        dataWrapper.errorMessage = Pair(""+e.message, -1)
                        data.value = dataWrapper
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    onHideLoader()
                    try {
                        dataWrapper.errorMessage = Pair(""+t.message, -1)
                        data.value = dataWrapper
                    }catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })
        } else {
            onHideLoader()
            dataWrapper.errorMessage = Pair((getApplication() as Context).getString(R.string.internet_connection_problem), 101)
            data.value = dataWrapper
        }
        return data
    }

    private fun onLoaderVisible(){
        loadingVisibility.value = true
    }

    private fun onHideLoader(){
        loadingVisibility.value = false
    }

    fun toRequestBody(value: String): RequestBody {
        val body = RequestBody.create(MediaType.parse("text/plain"), value)
        return body
    }

    private fun isInternetAvailable(): Boolean {
        return NetworkUtil.isNetConnected(getApplication())
    }
}