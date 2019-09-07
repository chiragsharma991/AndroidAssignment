package com.example.androidassignment.helper.adapter.binding


import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.androidassignment.R
import com.example.androidassignment.helper.adapter.extention.getParentActivity
import com.example.androidassignment.helper.adapter.extention.provideColor
import com.example.androidassignment.helper.adapter.imageloader.ImageLoader
import java.text.DecimalFormat

object CustomBindingAdapter {

    @BindingAdapter("selectable")
    @JvmStatic
    fun isVisible(view: ImageView, isSelect: Boolean) {
        view.isActivated = isSelect
    }




    @BindingAdapter("loadImageFromUri", "placeHolder")
    @JvmStatic
    fun loadImageFromUri(view: AppCompatImageView, uri: Uri?, placeholder: Drawable?) {
        ImageLoader.loadImageFromUri(view.context, uri, placeholder, view)
    }


    @BindingAdapter("setImage")
    @JvmStatic
    fun setImage(view: AppCompatImageView, image_path : String?) {
        Glide.with(view.context)
            .load(image_path?:"")
            .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.placeholder).error(R.drawable.placeholder))
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