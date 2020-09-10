package com.devtechdesign.imageannotation.ui.home

import android.graphics.Bitmap
import android.graphics.drawable.InsetDrawable
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
import com.devtechdesign.imageannotation.R
import com.devtechdesign.imageannotation.ui.AnnotatedImageView
import com.devtechdesign.imageannotation.ui.LineGeometryView
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

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

        var lineGeometryView = LineGeometryView(requireContext())
        val annotatedImageView: AnnotatedImageView? = root?.findViewById(R.id.annotatedImageView)
        annotatedImageView?.addGeometry(lineGeometryView)
        annotatedImageView?.setDragListner(object :
            AnnotatedImageView.IAnnotatedImageViewDragListener {
            override fun onDrag(x2: Float, y2: Float, bitmap: Bitmap?) {
                bitmap?.let {
                    Bitmap.createBitmap(it, x2.toInt() - 100, y2.toInt() - 100, 200, 200)?.let {
                        ivPreview.setImageBitmap(it)
                    }
                }
            }
        })
    }
}