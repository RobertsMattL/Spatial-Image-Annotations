package com.devtechdesign.imageannotation.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.devtechdesign.imageannotation.R
import kotlinx.android.synthetic.main.annotated_image_view.view.*

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

    fun addGeometry(geometryView: LineGeometryView) {
        var root = findViewById<ConstraintLayout>(R.id.root)
        val params = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        geometryView.layoutParams = params
        params.leftToLeft =  root.id
        params.topToTop = root.id
        params.bottomToBottom = root.id
        params.rightToRight = root.id

        disableGeometryViews(root)

        root.addView(geometryView)
    }

    private fun disableGeometryViews(root: ConstraintLayout) {
        for (child in root.children) {
            if (child is LineGeometryView) {
                child.isEnabled = false
            }
        }
    }
}
