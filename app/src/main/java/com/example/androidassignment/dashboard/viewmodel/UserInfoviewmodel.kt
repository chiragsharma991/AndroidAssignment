package com.example.androidassignment.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidassignment.base.BaseViewModel
import com.example.androidassignment.dashboard.pojo.UserInfomodel
import com.example.androidassignment.network.LiveDataWrapper

class UserInfoviewmodel(application: Application) : BaseViewModel(application)  {

    var userinfoviewmodel_list = MutableLiveData<LiveDataWrapper<UserInfomodel>>()


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

}
