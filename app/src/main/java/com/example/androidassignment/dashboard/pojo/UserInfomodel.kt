package com.example.androidassignment.dashboard.pojo

import com.example.androidassignment.storage.entity.UserInfoEntity

data class UserInfomodel(

    val rows: List<UserInfoEntity>,
    val title: String
)

data class Row(

    val description: String? = null,
    val imageHref: String? = null,
    val title: String? = null
)