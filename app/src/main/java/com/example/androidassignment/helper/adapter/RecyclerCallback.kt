package com.example.androidassignment.helper.adapter

import androidx.databinding.ViewDataBinding

/**
 * callback from universal adapter
 *
 * @param VM: view binding
 * @param T: type of model class
 *
 */

interface RecyclerCallback<VM : ViewDataBinding, T> {
    fun bindData(binder: VM, model: T)
}
