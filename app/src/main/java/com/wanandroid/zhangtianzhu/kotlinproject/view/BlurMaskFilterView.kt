package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BlurMaskFilterView (context: Context,attributeSet: AttributeSet):View(context,attributeSet) {

    private val paint = Paint()
    init {
        paint.color = Color.RED
        paint.isAntiAlias = true
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE,null)
        //内发光
//        paint.maskFilter = BlurMaskFilter(50f,BlurMaskFilter.Blur.INNER)
//        //外部发光
//        paint.maskFilter = BlurMaskFilter(50f,BlurMaskFilter.Blur.SOLID)
//        //内外发光
//        paint.maskFilter = BlurMaskFilter(50f,BlurMaskFilter.Blur.NORMAL)
//        //仅发光部分可见
        paint.maskFilter = BlurMaskFilter(50f,BlurMaskFilter.Blur.OUTER)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(100f,100f,100f,paint)
    }
}