package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.BounceInterpolator
import com.wanandroid.zhangtianzhu.kotlinproject.bean.Point
import com.wanandroid.zhangtianzhu.kotlinproject.utils.PointEvaluator

class MyPoint constructor(context: Context,attributeSet: AttributeSet):View(context,attributeSet) {
    private var curPoint:Point = Point(20f)
    private val paint: Paint = Paint()
    init {
        paint.color = Color.BLUE
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL_AND_STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(300f,300f,curPoint.getRadius(),paint)
    }

    fun doAnimator(){
        val animator = ValueAnimator.ofObject(PointEvaluator(),Point(20f),Point(200f))
        animator.addUpdateListener { valueAnimator ->
            curPoint = valueAnimator.animatedValue as Point
            invalidate()
        }
        animator.interpolator = BounceInterpolator()
        animator.duration = 3000
        animator.start()
    }
}