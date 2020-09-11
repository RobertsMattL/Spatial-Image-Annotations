How do I use ImageAnnotations?
------------------------------

```kotlin
        var lineGeometryView =
            LineGeometryView(
                requireContext()
            )
            
        val annotatedImageView: AnnotatedImageView? = root?.findViewById(R.id.annotatedImageView)
        annotatedImageView?.addGeometry(lineGeometryView)
        annotatedImageView?.setDragListner(object :
            AnnotatedImageView.IDragListener {
            override fun onDrag(x2: Float, y2: Float, bitmap: Bitmap?) {
                //do stuff
            }
        })
```