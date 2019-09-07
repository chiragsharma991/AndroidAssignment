package com.example.androidassignment.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidassignment.base.BaseViewModel
import com.example.androidassignment.dataclass.UserInfomodel
import com.example.androidassignment.rest.LiveDataWrapper

class UserInfoviewmodel(application: Application) : BaseViewModel(application)  {

    var userinfoviewmodel_list = MutableLiveData<LiveDataWrapper<UserInfomodel>>()


    /**
     * Get UserInfo list
     * @param appliedForce: If API call is forcefully applied
     */

    fun getUserInfo(appliedForce : Boolean) : LiveData<LiveDataWrapper<UserInfomodel>> {
        Log.e("TAG","getUserInfo---"+userinfoviewmodel_list.value)

        if(userinfoviewmodel_list.value == null || appliedForce){
            userinfoviewmodel_list = callAPI(webservice?.userinfo()!!)
        }
        return userinfoviewmodel_list
    }

}
