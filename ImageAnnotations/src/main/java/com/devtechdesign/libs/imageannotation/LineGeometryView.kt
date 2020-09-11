package com.devtechdesign.libs.imageannotation

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


class LineGeometryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var dragListner: IViewDragListener? = null
    private val text: String = "65' 5\""
    private var lineInitialized = false
    private val STROKE_WIDTH: Float = 4f
    private val TEXT_STROKE_WIDTH: Float = 1f
    private val TEXT_SIZE: Float = 24f

    var paint: Paint = Paint()
    var dragPointPaint: Paint = Paint()
    var textPaint: TextPaint = TextPaint()
    var x1 = 0f
    var y1: Float = 0f
    var x2: Float = 0f
    var y2: Float = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (isEnabled) {
            Log.d("ontouch", event?.action.toString())

            event?.let {
                when (event.action and MotionEvent.ACTION_MASK) {

                    MotionEvent.ACTION_DOWN -> {
                        if (!lineInitialized) {
                            initializeLine(event)
                        }
                    }

                    MotionEvent.ACTION_MOVE -> {
                        x2 = event.x
                        y2 = event.y
                        dragListner?.onDrag(x2, y2)
                        this.invalidate()
                    }
                }
            }
        }
        return true
    }

    private fun initializeLine(event: MotionEvent) {
        x1 = event.x
        y1 = event.y
        x2 = event.x + 100
        y2 = event.y + 100
        lineInitialized = true
        this.invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawLine(x1, y1, x2, y2, paint)
        canvas.drawCircle(x1, y1, 10f, dragPointPaint)
        canvas.drawCircle(x2, y2, 10f, dragPointPaint)

        /*
            draw text
         */
        val textHeight = textPaint.descent() - textPaint.ascent()
        val textOffset = textHeight - textPaint.descent() - 1

        val path = Path()
        path.moveTo(x1, y1)
        path.lineTo(x2, y2)
        path.close()

        var dist = calculateDistanceBetweenPoints(
            x1.toDouble(),
            y1.toDouble(),
            x2.toDouble(),
            y2.toDouble()
        )

        var horizontalTextPosition = (dist / 2).toFloat()
        canvas.drawTextOnPath(
            text,
            path,
            -horizontalTextPosition,
            textHeight,
            textPaint
        )

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

        setupLinePaint()
        setupTextPaint()

        dragPointPaint.isAntiAlias = true
        dragPointPaint.isDither = true

        dragPointPaint.color = Color.BLACK
        dragPointPaint.strokeWidth = 2f
        dragPointPaint.setStyle(Paint.Style.STROKE)
        dragPointPaint.isAntiAlias = true
        dragPointPaint.isDither = true
        dragPointPaint.setPathEffect(DashPathEffect(floatArrayOf(5f, 5f), 0f))
    }

    private fun setupLinePaint() {
        paint.color = Color.GREEN
        paint.strokeWidth = STROKE_WIDTH
        paint.style = Paint.Style.FILL_AND_STROKE;
        paint.isAntiAlias = true;
        paint.isFilterBitmap = true;
        paint.isDither = true;
    }

    private fun setupTextPaint() {
        textPaint.color = Color.GREEN
        textPaint.strokeWidth = TEXT_STROKE_WIDTH
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = TEXT_SIZE
        textPaint.isDither = true
        textPaint.isAntiAlias = true
    }

    fun setDragListner(iViewDragListener: IViewDragListener) {
        this.dragListner = iViewDragListener
    }

    interface IViewDragListener{
        fun onDrag(x2: Float, y2: Float)
    }
}
