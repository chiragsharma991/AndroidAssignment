package com.example.androidassignment.widget


import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat
import com.example.androidassignment.R
import com.example.androidassignment.base.BaseActivity
import kotlinx.android.synthetic.main.toolbar.view.*


/**
 * Custom view for toolbar
 */

class Toolbar : FrameLayout, View.OnClickListener {

    var title: String? = null
        set(value) {
            txt_title.text = value
            field = value
        }

    var titleTextColor: Int? = null
        set(value) {
            txt_title.setTextColor(value!!)
            field = value
        }
    var tintLeftIcon: Int? = null
        set(value) {
            ImageViewCompat.setImageTintList(img_back, ColorStateList.valueOf(value!!))
            field = value
        }

    var showBack: Boolean = true
        set(value) {
            img_back.visibility = if (value) View.VISIBLE else View.GONE
            field = value
        }

    var toolbarBackground: Int = Color.WHITE
        set(value) {
            my_toolbar.setBackgroundColor(value)
        }

    var backDrawable: Int = R.drawable.close
        set(value) {
            img_back.setImageResource(value)
        }

    var fontStyle: Int = R.font.mavenpro_bold
        set(value) {
            txt_title.typeface = ResourcesCompat.getFont(context, value)
        }

    var titleSize: Int = R.dimen._20sdp
        set(value) {
            txt_title.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimensionPixelSize(value).toFloat()
            )
        }

    var isShowSave: Boolean = false
        set(value) {
            txt_save.visibility = if (value) View.VISIBLE else View.GONE
            field = value
        }

    var textSave: String = context.getString(R.string.save)
        set(value) {
            txt_save.text = value
            field = value
        }

    var isEditProfile: Boolean = false
        set(value) {
            iv_edit_profile.visibility = if (value) View.VISIBLE else View.GONE
            field = value
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        if (!isInEditMode) {
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
        }

        inflate(context, R.layout.toolbar, this)

        // Load attributes
        val a = context.obtainStyledAttributes(attrs, R.styleable.Toolbar, defStyle, 0)

        title = a.getString(R.styleable.Toolbar_android_title)
        showBack = a.getBoolean(R.styleable.Toolbar_showBack, true)
        toolbarBackground = a.getColor(R.styleable.Toolbar_backgroundColor, Color.WHITE)
        backDrawable = a.getResourceId(R.styleable.Toolbar_backDrawable, R.drawable.close)
        titleTextColor = a.getColor(R.styleable.Toolbar_titleTextColor, Color.BLACK)
        tintLeftIcon = a.getColor(R.styleable.Toolbar_tintLeftIcon, Color.BLACK)
        fontStyle = a.getResourceId(R.styleable.Toolbar_textStyle, R.font.mavenpro_bold)
        titleSize = a.getResourceId(R.styleable.Toolbar_titelSize, R.dimen._20sdp)
        isShowSave = a.getBoolean(R.styleable.Toolbar_isShowSave, false)
        isEditProfile = a.getBoolean(R.styleable.Toolbar_isShowEditProfile, false)

        a.getString(R.styleable.Toolbar_textSave).apply {
            this?.let {
                textSave = it
            }
        }

        setListeners()
        a.recycle()
    }

    fun saveTextChange(text: String){
        txt_save.text = text
    }

    private fun setListeners() {
        img_back.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val baseAct = context as? BaseActivity
        when (v.id) {
            R.id.img_back -> {
                baseAct?.onBackPressed()
            }
        }
    }

    fun getBackBtn() = this.img_back

    fun getSaveBtn() = this.txt_save

    interface OnClickListenerToolbar {
        fun onClickBack()
        fun onClickSetting()
    }
}

