package com.devtechdesign.imageannotation.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class DrawView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var firstPointSet = false
    private val STROKE_WIDTH: Float = 4f
    var paint: Paint = Paint()
    var x1 = 0f
    var y1: Float = 0f
    var x2: Float = 0f
    var y2: Float = 0f
    var i = 1

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("ontouch", event?.action.toString())

        event?.let {
            when (event.action and MotionEvent.ACTION_MASK) {

                MotionEvent.ACTION_DOWN ->{
                    if(!firstPointSet) {
                        x1 = event.x
                        y1 = event.y
                        firstPointSet = true
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                        x2 = event.x
                        y2 = event.y
                        this.invalidate()
                    }
                }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawLine(x1, y1, x2, y2, paint)
        i = 1
    }


    init {
        paint.color = Color.GREEN
        paint.setStyle(Paint.Style.STROKE);
        paint.strokeWidth = STROKE_WIDTH
    }
}