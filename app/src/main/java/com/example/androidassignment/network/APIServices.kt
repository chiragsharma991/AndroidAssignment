package com.example.androidassignment.network

import com.example.androidassignment.dashboard.pojo.UserInfomodel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded





interface APIServices {

    /**
     * define your get/post api here
     */

    @GET("/s/2iodh4vg0eortkl/facts.json")
    fun userinfo (): Call<UserInfomodel>

    ///maps/api/geocode/json?address={zipcode}&sensor=false"
    @GET("/maps/api/geocode/json?sensor=false")
    fun getPositionByZip(@Query("address") address: String)

    @POST("login")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    fun login(@Body jsonObject: JsonObject): Call<JsonObject>

    @GET("users/157701325/productSubCategory/1/product")
    fun getsubcat(
        @Header("authorization") token: String,
        @QueryMap params: Map<String, String>
    ): Call<JsonObject>

    @FormUrlEncoded
    @POST("Category/category/menu")
    fun setProductList(
        @Field("r_token") r_token: String ,
        @Field("r_key") r_key: String,
        @Field("customer_id") customer_id: String,
        @Field("app") app : String
    ): Call<JsonObject>

    @GET("/maps/api/geocode/json")
    @FormUrlEncoded
    fun getPositionByZip(@FieldMap params: Map<String, String>)

}