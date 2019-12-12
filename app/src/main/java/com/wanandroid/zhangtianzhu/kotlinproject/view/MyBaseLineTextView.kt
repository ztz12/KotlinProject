package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class MyBaseLineTextView constructor(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val paint = Paint()
    private val baseLineX = 0f
    private val baseLineY = 200f
    private val text = "harvic\'s blog"

    init {
        paint.color = Color.GREEN
    }

    /**
     * 得到Text四线格的各线位置
    ascent线Y坐标 = baseline线Y坐标 + fontMetric.ascent;
    推算过程如下：
    因为ascent线的Y坐标等于baseline线的Y坐标减去从baseline线到ascent线的这段距离。
    也就是：(|fontMetric.ascent|表示取绝对值)
    ascent线Y坐标 = baseline线Y坐标 - |fontMetric.ascent|;
    又因为fontMetric.ascent是负值，所以：
    ascent线Y坐标 = baseline线Y坐标 - |fontMetric.ascent|;
    ascent线Y坐标 = baseline线Y坐标 - （ -fontMetric.ascent）;
    ascent线Y坐标 = baseline线Y坐标 + fontMetric.ascent;
    这就是整个推算过程，没什么难度，同理可以得到：

    ascent线Y坐标 = baseline线的y坐标 + fontMetric.ascent；
    descent线Y坐标 = baseline线的y坐标 + fontMetric.descent；
    top线Y坐标 = baseline线的y坐标 + fontMetric.top；
    bottom线Y坐标 = baseline线的y坐标 + fontMetric.bottom；
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.textSize = 120f
        paint.color = Color.BLUE
        //设置文本绘制位置，从左边开始
//        paint.textAlign = Paint.Align.LEFT
        //从中心开始绘制，相对位置根据所在矩形计算
//        paint.textAlign = Paint.Align.CENTER
//        canvas.drawText(text, baseLineX, baseLineY, paint)

//        //计算各基线位置
        val fontMetrics = paint.fontMetrics
        val ascent = baseLineY + fontMetrics.ascent
        val descent = baseLineY + fontMetrics.descent
        val top = baseLineY + fontMetrics.top
        val bottom = baseLineY + fontMetrics.bottom
//
//        //画基线
//        canvas.drawLine(baseLineX, baseLineY, 3000f, baseLineY, paint)
//
//        //画top
//        paint.color = Color.GREEN
//        canvas.drawLine(baseLineX, top, 3000f, top, paint)
//        //画ascent
//        paint.color = Color.RED
//        canvas.drawLine(baseLineX, ascent, 3000f, ascent, paint)
//        //画descent
//        paint.color = Color.YELLOW
//        canvas.drawLine(baseLineX, descent, 3000f, descent, paint)
//        //画bottom
//        paint.color = Color.BLACK
//        canvas.drawLine(baseLineX, bottom, 3000f, bottom, paint)

        //画Text文本所占区域
        val width = paint.measureText(text).toInt()
        val rect = Rect(baseLineX.toInt(), top.toInt(), baseLineX.toInt() + width, bottom.toInt())
        paint.color = Color.GREEN
        canvas.drawRect(rect, paint)

        //画最小矩形
        val minRect = Rect()
        paint.getTextBounds(text, 0, text.length, minRect)
        minRect.top += baseLineY.toInt()
        minRect.bottom += baseLineY.toInt()

        paint.color = Color.RED
        canvas.drawRect(minRect, paint)

        paint.color = Color.BLACK
        canvas.drawText(text, baseLineX, baseLineY, paint)
    }
}