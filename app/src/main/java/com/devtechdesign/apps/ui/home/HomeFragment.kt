package com.devtechdesign.apps.ui.home

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devtechdesign.apps.R
import com.devtechdesign.libs.imageannotation.AnnotatedImageView
import com.devtechdesign.libs.imageannotation.LineOverlay
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    var PREVIEW_WIDTH = 200
    var PREVIEW_HEIGHT = 200
    private var root: View? = null
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        this.root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root?.findViewById(R.id.text_home)!!
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })


        val btnAdd: Button = root?.findViewById(R.id.btnAdd)!!
        btnAdd.setOnClickListener {
            onAddButtonClick()
        }
        return root
    }

    private fun onAddButtonClick() {
        Toast.makeText(context, R.string.click_image_to_begin_adding_geometry, Toast.LENGTH_SHORT)
            .show()

        var lineGeometryView =
            LineOverlay(
                requireContext()
            )

        val annotatedImageView: AnnotatedImageView? = root?.findViewById(R.id.annotatedImageView)
        annotatedImageView?.setImageResource(R.drawable.beam_sturcture)
        annotatedImageView?.addGeometry(lineGeometryView)

        annotatedImageView?.setDragListner(object :
            AnnotatedImageView.IDragListener {
            override fun onDrag(x2: Float, y2: Float, bitmap: Bitmap?) {
                bitmap?.let {
                    var xPreview = x2.toInt() - 100
                    var yPreview = y2.toInt() - 100
                    if (xPreview > 0 && yPreview > 0 && xPreview + PREVIEW_WIDTH <= bitmap.width && yPreview + PREVIEW_HEIGHT <= bitmap.height)
                        Bitmap.createBitmap(it, xPreview, yPreview, PREVIEW_WIDTH, PREVIEW_HEIGHT)
                            ?.let {
                                ivPreview.setImageBitmap(it)
                            }
                }
            }
        })
    }
}