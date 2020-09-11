package com.devtechdesign.libs.imageannotation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children

class AnnotatedImageView : FrameLayout {

    private lateinit var dragListener: IDragListener

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

    fun addGeometry(geometryView: LineOverlay) {
        var root = findViewById<ConstraintLayout>(R.id.root)
        val params = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        )


        geometryView.setDragListner(object : LineOverlay.IViewDragListener {
            override fun onDrag(x2: Float, y2: Float) {
                var bitmap = loadBitmapFromView(root)
                dragListener.onDrag(x2, y2, bitmap)
            }
        })


        geometryView.layoutParams = params
        params.leftToLeft = root.id
        params.topToTop = root.id
        params.bottomToBottom = root.id
        params.rightToRight = root.id

        disableGeometryViews(root)

        root.addView(geometryView)
    }

    private fun disableGeometryViews(root: ConstraintLayout) {
        for (child in root.children) {
            if (child is LineOverlay) {
                child.isEnabled = false
            }
        }
    }

    fun loadBitmapFromView(view: View): Bitmap? {
        //Get the dimensions of the view so we can re-layout the view at its current size
        //and create a bitmap of the same size
        val width = view.width
        val height = view.height
        val measuredWidth = MeasureSpec.makeMeasureSpec(
            width,
            MeasureSpec.EXACTLY
        )
        val measuredHeight = MeasureSpec.makeMeasureSpec(
            height,
            MeasureSpec.EXACTLY
        )

        //Cause the view to re-layout
        view.measure(measuredWidth, measuredHeight)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)

        //Create a bitmap backed Canvas to draw the view into
        val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)

        //Now that the view is laid out and we have a canvas, ask the view to draw itself into the canvas
        view.draw(c)
        return b
    }

    fun setDragListner(iViewDragListener: IDragListener) {
        this.dragListener = iViewDragListener
    }

    interface IDragListener {
        fun onDrag(x2: Float, y2: Float, bitmap: Bitmap?)
    }
}
