package com.example.androidassignment.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidassignment.base.BaseViewModel
import com.example.androidassignment.dashboard.pojo.UserInfomodel
import com.example.androidassignment.network.LiveDataWrapper
import com.google.gson.JsonObject

class UserInfoviewmodel(application: Application) : BaseViewModel(application)  {

    var userinfoviewmodel_list = MutableLiveData<LiveDataWrapper<UserInfomodel>>()
    var usersubcat_list = MutableLiveData<LiveDataWrapper<JsonObject>>()


    /**
     * Get UserInfo list
     * @param appliedForce: If API call is forcefully applied
     */

    fun getUserInfo(appliedForce : Boolean) : LiveData<LiveDataWrapper<UserInfomodel>> {

        if(userinfoviewmodel_list.value == null || appliedForce){
            userinfoviewmodel_list = callAPI(webservice?.userinfo()!!)
        }
        return userinfoviewmodel_list
    }


    fun getsubcat() : LiveData<LiveDataWrapper<JsonObject>>{
        val map = HashMap<String,String>()
        map.put("productSubCategoryId","7")
        map.put("productId","2")
        usersubcat_list = callAPI(webservice?.getsubcat("Bearer aHsLqO3CcdzwNGBGS2mpuKuFMxhp4t7odc-uKUp98V9n4vIauYD6g-dZU11YbbbTFeJCaMLh5wxS-EvOylyjcxdfSJYcua8YQgYTY6u10EhPlbVLyV3Kk9Gq4IerfWrpKeqb3i3PguKANDU9KoRrH4a8v2kAZ1BrzhMhbGSxlZgSjCM8CEsly1K8cu0RL-pigdsOi7mIAeiBcdvCGhzoe3G2c22JkgoAE8sh0WHdQtw",map)!!)
        return usersubcat_list


    }

}
