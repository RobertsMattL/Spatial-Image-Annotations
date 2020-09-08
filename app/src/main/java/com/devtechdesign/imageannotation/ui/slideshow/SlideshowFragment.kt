package com.devtechdesign.imageannotation.ui.slideshow

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devtechdesign.imageannotation.R


class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        val textView: TextView = root.findViewById(R.id.tvTitle)
        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        var iv = root.findViewById<ImageView>(R.id.ivImageView)
        drawLineOnImage(iv)

        return root
    }

    fun drawLineOnImage(imageView : ImageView)
    {

        var bitmap = convertViewToDrawable(imageView)
//        val bitmap: Bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.setColor(Color.GREEN)
        canvas.drawLine(50f, 50f, 10f,10f, paint)
        imageView.setImageBitmap(bitmap)
    }

    private fun convertViewToDrawable(view: View): Bitmap {
        val spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(spec, spec)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        val b = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight,
                Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        c.translate((-view.scrollX).toFloat(), (-view.scrollY).toFloat())
        view.draw(c)
        return b
    }

}