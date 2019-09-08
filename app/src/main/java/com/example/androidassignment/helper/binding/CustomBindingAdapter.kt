package com.example.androidassignment.helper.binding


import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.androidassignment.R
import com.example.androidassignment.helper.extention.getParentActivity
import com.example.androidassignment.helper.imageloader.ImageLoader

// Handle all this event from xml

object CustomBindingAdapter {


    /**
     * This method used for loading image from server with ic_placeholder
     * @param image_path: image url
     *
     */

    @BindingAdapter("setImage")
    @JvmStatic
    fun setImage(view: AppCompatImageView, image_path : String?) {
        Glide.with(view.context)
            .load(image_path?:"")
            .apply(RequestOptions().placeholder(R.color.colorGreyLite).error(R.color.colorGreyLite))
            .into(view)

    }

    /**
     * Visibility handle from view model
     */
    @BindingAdapter("mutableVisibility")
    @JvmStatic
    fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
        val parentActivity: AppCompatActivity? = view.getParentActivity()
        if(parentActivity != null && visibility != null) {
            visibility.observe(parentActivity, Observer { value -> view.visibility = value?:View.VISIBLE})
        }
    }

}