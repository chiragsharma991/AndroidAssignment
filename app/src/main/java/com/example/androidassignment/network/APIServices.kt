package com.example.androidassignment.network

import com.example.androidassignment.dashboard.pojo.UserInfomodel
import retrofit2.Call
import retrofit2.http.GET

interface APIServices {

    /**
     * define your get/post api here
     */

    @GET("/s/2iodh4vg0eortkl/facts.json")
    fun userinfo (): Call<UserInfomodel>
}