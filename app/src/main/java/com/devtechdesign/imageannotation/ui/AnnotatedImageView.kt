package com.devtechdesign.imageannotation.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import com.devtechdesign.imageannotation.R

class AnnotatedImageView : FrameLayout {

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        inflate(context, R.layout.annotated_image_view, this)
    }

    fun setImageBitmap(bitmap: Bitmap) {
        findViewById<ImageView>(R.id.imageView).setImageBitmap(bitmap)
    }

    fun setImageDrawable(drawable: Drawable) {
        findViewById<ImageView>(R.id.imageView).setImageDrawable(drawable)
    }
}
