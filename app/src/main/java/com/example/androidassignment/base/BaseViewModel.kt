package com.example.androidassignment.base


import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.androidassignment.R
import com.example.androidassignment.rest.APIServices
import com.example.androidassignment.rest.ApiRetrofit
import com.example.androidassignment.rest.LiveDataWrapper
import com.example.androidassignment.utils.NetworkUtil
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseViewModel(application: Application) : AndroidViewModel(application){

    var webservice: APIServices? = ApiRetrofit.retrofitInstance?.create(APIServices::class.java)

    /**
     * Used via data binding in xml for showing progressbar once api call
     */
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

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
                            /*errorHandle(mErrorBody, response.code())*/
                        }
                        data.value = dataWrapper
                    } catch (e: Exception) {
                        dataWrapper.errorMessage = Pair(""+e.message, -1)
                        data.value = dataWrapper
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    Log.e("onFailure", "" + t.message)
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
            dataWrapper.errorMessage = Pair((getApplication() as Context).getString(R.string.internet_connection_problem), 101)
            data.value = dataWrapper
        }
        return data
    }

    private fun onLoaderVisible(){
        loadingVisibility.value = View.VISIBLE
    }

    private fun onHideLoader(){
        loadingVisibility.value = View.GONE
    }

    fun toRequestBody(value: String): RequestBody {
        val body = RequestBody.create(MediaType.parse("text/plain"), value)
        return body
    }

    private fun isInternetAvailable(): Boolean {
        return NetworkUtil.isNetConnected(getApplication())
    }
}