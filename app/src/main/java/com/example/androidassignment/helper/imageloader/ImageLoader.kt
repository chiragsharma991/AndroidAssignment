package com.example.androidassignment.helper.imageloader


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*


object ImageLoader {

    /**
     * Load image with round shape
     */
    fun loadImageCircleFromResource(context: Context, resourceId: Int, placeholderImg: Int, imageView: ImageView) {
        Glide.with(context).load(resourceId).apply(RequestOptions.circleCropTransform().placeholder(placeholderImg))
            .into(imageView)
    }

    /**
     * define random colour here
     */

    private val vibrantLightColorList = arrayOf(ColorDrawable(Color.parseColor("#F0EFF5")), ColorDrawable(
        Color.parseColor("#F0EFF5")), ColorDrawable(Color.parseColor("#F0EFF5")), ColorDrawable(
        Color.parseColor("#F0EFF5")), ColorDrawable(Color.parseColor("#F0EFF5")))

    /**
     * get random drawable color
     */

    fun getRandomDrawbleColor(): ColorDrawable {
        val idx = Random().nextInt(vibrantLightColorList.size)
        return vibrantLightColorList[idx]
    }

}