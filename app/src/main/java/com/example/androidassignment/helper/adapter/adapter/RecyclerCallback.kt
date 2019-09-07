package com.example.androidassignment.helper.adapter.adapter

import androidx.databinding.ViewDataBinding


interface RecyclerCallback<VM : ViewDataBinding, T> {
    fun bindData(binder: VM, model: T)
}
