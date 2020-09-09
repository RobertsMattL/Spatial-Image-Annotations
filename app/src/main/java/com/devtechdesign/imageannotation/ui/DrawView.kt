package com.devtechdesign.imageannotation.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


class DrawView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val text: String = "65' 5\""
    private var firstPointSet = false
    private val STROKE_WIDTH: Float = 4f
    private val TEXT_STROKE_WIDTH: Float = 4f
    var paint: Paint = Paint()
    var textPaint: Paint = Paint()
    var x1 = 0f
    var y1: Float = 0f
    var x2: Float = 0f
    var y2: Float = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("ontouch", event?.action.toString())

        event?.let {
            when (event.action and MotionEvent.ACTION_MASK) {

                MotionEvent.ACTION_DOWN -> {
                    if (!firstPointSet) {
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
        canvas.drawCircle(x1, y1, 5f, paint)
        canvas.drawCircle(x2, y2, 5f, paint)

        /*
            draw text
         */
        val textPaint = TextPaint()
        textPaint.color = Color.GREEN
        textPaint.textAlign = Paint.Align.RIGHT
        textPaint.strokeWidth = STROKE_WIDTH


        val textHeight = textPaint.descent() - textPaint.ascent()
        val textOffset = textHeight - textPaint.descent()

        val path = Path()
        path.moveTo(x1, y1)
        path.lineTo(x2, y2)
        path.close()

        var dist = calculateDistanceBetweenPoints(x1.toDouble(),y1.toDouble(),x2.toDouble(), y2.toDouble())

        var horizontalTextPosition = (dist/2).toFloat()
        canvas.drawTextOnPath(text, path, -horizontalTextPosition, textHeight + textOffset, textPaint)

    }

    fun calculateDistanceBetweenPoints(
        x1: Double,
        y1: Double,
        x2: Double,
        y2: Double
    ): Double {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1))
    }


    init {
        paint.color = Color.GREEN
        paint.strokeWidth = STROKE_WIDTH
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);

        textPaint.color = Color.GREEN
        textPaint.strokeWidth = TEXT_STROKE_WIDTH
    }
}