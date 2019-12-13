package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class PaintView constructor(context: Context,attributeSet: AttributeSet):View(context,attributeSet){

    private val paint = Paint()
    private val path = Path()

    private var value = 0f

    init {
        paint.color = Color.GREEN
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = 50f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        //无线帽
//        paint.strokeCap = Paint.Cap.BUTT
//        canvas.drawLine(100f,100f,500f,100f,paint)
//
//        //方形线帽
//        paint.strokeCap = Paint.Cap.SQUARE
//        canvas.drawLine(100f,300f,500f,300f,paint)
//
//        //圆形线帽
//        paint.strokeCap = Paint.Cap.ROUND
//        canvas.drawLine(100f,500f,500f,500f,paint)
//
//        paint.color = Color.RED
//        paint.style = Paint.Style.STROKE
//        paint.strokeWidth = 5f
//        canvas.drawLine(100f,100f,100f,800f,paint)

        paint.style = Paint.Style.STROKE
//
//        path.moveTo(100f,100f)
//        path.lineTo(400f,100f)
//        path.lineTo(100f,300f)
//        //结合处为锐角
//        paint.strokeJoin = Paint.Join.MITER
//        canvas.drawPath(path,paint)
//
//        path.moveTo(100f,400f)
//        path.lineTo(400f,400f)
//        path.lineTo(100f,600f)
//        //结合处为直线
//        paint.strokeJoin = Paint.Join.BEVEL
//        canvas.drawPath(path, paint)
//
//        path.moveTo(100f,700f)
//        path.lineTo(400f,700f)
//        path.lineTo(100f,800f)
//        //结合处为圆角
//        paint.strokeJoin = Paint.Join.ROUND
//        canvas.drawPath(path, paint)

        paint.strokeWidth = 4f
        path.moveTo(100f,600f)
        path.lineTo(300f,100f)
        path.lineTo(500f,700f)
//
//        canvas.drawPath(path,paint)
//
//        //设置圆形拐角
//        paint.color = Color.RED
//        paint.pathEffect = CornerPathEffect(100f)
//        canvas.drawPath(path, paint)
//
//        paint.color = Color.YELLOW
//        paint.pathEffect = CornerPathEffect(200f)
//        canvas.drawPath(path, paint)

//        DashPathEffect 虚线效果 长度必须等于大于2，一个为实线一个为虚线；个数必须为偶数，为奇数的话，忽略最后一个数字，因为实线与虚线都是成对存在的
        //画线段，不偏移
        paint.color = Color.YELLOW
        paint.pathEffect = DashPathEffect(floatArrayOf(value+20,value+10,value+100,value+100),0f)
        canvas.translate(0f,100f)
        canvas.drawPath(path, paint)

        //偏移 15f
        paint.color = Color.BLUE
        paint.pathEffect = DashPathEffect(floatArrayOf(20f,10f,50f,50f),15f)
        canvas.translate(0f,100f)
        canvas.drawPath(path, paint)
    }

    fun doAnimator(){
        val animator = ValueAnimator.ofFloat(230f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.addUpdateListener { valueAnimator ->
            value = valueAnimator.animatedValue as Float
            invalidate()
        }
        animator.start()
    }
}