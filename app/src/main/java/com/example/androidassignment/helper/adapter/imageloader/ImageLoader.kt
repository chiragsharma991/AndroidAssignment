package com.example.androidassignment.helper.adapter.imageloader


import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.request.transition.ViewPropertyTransition
import java.util.*


/**
 * Created by mht on 24/02/18.
 */
object ImageLoader {

    /**
     * Load image with round shape
     */
    fun loadImageCircleFromResource(context: Context, resourceId: Int, placeholderImg: Int, imageView: ImageView) {
        Glide.with(context).load(resourceId).apply(RequestOptions.circleCropTransform().placeholder(placeholderImg))
            .into(imageView)
    }


    fun loadImageFromUri(context: Context, fromFile: Uri?, placeholderImg: Drawable?, imageView: ImageView) {
        Glide.with(context).load(fromFile).apply(RequestOptions().placeholder(placeholderImg)).into(imageView)
    }


    var animationObject: ViewPropertyTransition.Animator = ViewPropertyTransition.Animator { view ->
        view.alpha = 0f
        val fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        fadeAnim.duration = 1000
        fadeAnim.start()
    }

    private fun validImage(path: String): Boolean {
        return path.contains("png") || path.contains("jpg") || path.contains("PNG") || path.contains("JPG") || path.contains(
            "JPEG"
        ) || path.contains("jpeg")

    }

    private val vibrantLightColorList = arrayOf(ColorDrawable(Color.parseColor("#F0EFF5")), ColorDrawable(
        Color.parseColor("#F0EFF5")), ColorDrawable(Color.parseColor("#F0EFF5")), ColorDrawable(
        Color.parseColor("#F0EFF5")), ColorDrawable(Color.parseColor("#F0EFF5")))


    fun getRandomDrawbleColor(): ColorDrawable {
        val idx = Random().nextInt(vibrantLightColorList.size)
        return vibrantLightColorList[idx]
    }

    interface OnBitmapReady {
        fun onBitmapReady(bitmap: Bitmap)
    }
}