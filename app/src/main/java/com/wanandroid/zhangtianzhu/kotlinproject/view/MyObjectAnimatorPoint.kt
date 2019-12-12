package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.wanandroid.zhangtianzhu.kotlinproject.bean.Point

class MyObjectAnimatorPoint constructor(context: Context,attributeSet: AttributeSet):View(context,attributeSet){
    private val paint = Paint()
    private val curPoint = Point(20f)
    init {
        paint.color = Color.YELLOW
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(300f,300f,curPoint.getRadius(),paint)
    }

    fun getPointRadius():Int{
        return 50
    }
    //设置objectAnimator 需要的set方法属性
    fun setPointRadius(radius:Float){
        curPoint.setRadius(radius)
        invalidate()
    }
}