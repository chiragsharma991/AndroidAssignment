package com.example.androidassignment.rest

import android.text.TextUtils
import com.example.androidassignment.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiRetrofit {

    private var retrofit: Retrofit? = null
    private const val TIME_OUT_VALUE = 120
    private val TIME_OUT_UNIT = TimeUnit.SECONDS
    val BASE_URL = "https://dl.dropboxusercontent.com"


    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                val retrofitBuilder = Retrofit.Builder()
                retrofitBuilder.baseUrl(BASE_URL)
                val gson = GsonBuilder().setPrettyPrinting().create()
                retrofitBuilder.addConverterFactory(GsonConverterFactory.create(gson))
                retrofitBuilder.client(client)
                retrofit = retrofitBuilder.build()
            }
            return retrofit
        }

    private val client: OkHttpClient
        get() {
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(TIME_OUT_VALUE.toLong(), TIME_OUT_UNIT)
            httpClient.connectTimeout(TIME_OUT_VALUE.toLong(), TIME_OUT_UNIT)
            httpClient.addInterceptor { chain ->
                val original = chain.request()

                val request = original.newBuilder()
                request.header("Accept", "application/json")
              //  if(!TextUtils.isEmpty(SharedPreferenceUtil.getString(SharedPreferenceUtil.API_TOKEN, "")))
                 //   request.header("Authorization", "Bearer ${SharedPreferenceUtil.getString(SharedPreferenceUtil.API_TOKEN, "")}")
                request.method(original.method(), original.body())

                chain.proceed(request.build())
            }

            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(interceptor)
            }

            return httpClient.build()
        }

    fun reInitilizeRetrofit(){
        retrofit = null
    }
}