package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class WaveViewTwo constructor(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet) {
    private val paint = Paint()
    private val path = Path()

    private var moveDistance = 0

    private val waveLength = 400f
    private val origY = 700f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //清除路径重新绘制，否则是矩形
        path.reset()

        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = Color.RED

        val control = waveLength / 2
        //移动到坐标-400,700，横向移动波浪
//        path.moveTo(-waveLength + moveDistance, origY)
        //垂直方向移动波浪
        path.moveTo(-waveLength + moveDistance, origY - moveDistance)
        //用循环画出满屏幕的波浪
        for (i in -400..(width + waveLength).toInt() step waveLength.toInt()) {
            path.rQuadTo(control / 2, -70f, control, 0f)
            path.rQuadTo(control / 2, 70f, control, 0f)
        }

        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(0f, height.toFloat())
        path.close()

        canvas.drawPath(path, paint)
    }

    fun startValueAnimator() {
        val moveAnimator = ValueAnimator.ofInt(0, 400)
        moveAnimator.duration = 2500
        //表示无限循环
        moveAnimator.repeatCount = ValueAnimator.INFINITE
        moveAnimator.interpolator = LinearInterpolator()

        moveAnimator.addUpdateListener { animation ->
            moveDistance = animation.animatedValue as Int
            invalidate()
        }
        moveAnimator.start()
    }
}