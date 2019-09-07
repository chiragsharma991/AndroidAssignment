package com.example.androidassignment.rest

import com.example.androidassignment.dataclass.UserInfomodel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIServices {


    @GET("/s/2iodh4vg0eortkl/facts.json")
    fun userinfo (): Call<UserInfomodel>
}