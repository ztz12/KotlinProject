package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class SelfTextView constructor(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val paint = Paint()
    //每个字体的位置，两两为一组
    private val pos = floatArrayOf(80f, 100f, 80f, 200f, 80f, 300f, 80f, 400f)
    private val str = "风萧萧兮易水寒，壮士一去兮不复返"
    private val circlePath1 = Path()
    private val circlePath2 = Path()

    init {
        paint.color = Color.RED
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.textSize = 50f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPosText("画图实例", pos, paint)
        //CCW 表示逆时针，CW表示顺时针
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        circlePath1.addCircle(200f, 300f, 150f, Path.Direction.CCW)
        canvas.drawPath(circlePath1, paint)
        circlePath2.addCircle(550f, 300f, 150f, Path.Direction.CCW)
        canvas.drawPath(circlePath2, paint)

        paint.color = Color.BLUE
        //hOffset 与路径起始点水平距离，vOffset与路径中心垂直偏移量
        paint.strokeWidth = 0f
        canvas.drawTextOnPath(str, circlePath1, 0f, 0f, paint)
        canvas.drawTextOnPath(str, circlePath2, 80f, 30f, paint)
    }
}