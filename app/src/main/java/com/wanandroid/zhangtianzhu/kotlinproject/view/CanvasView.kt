package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CanvasView constructor(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val paint = Paint()
    private var bitmap: Bitmap
    private var bitmapCanvas: Canvas

    init {
        paint.color = Color.YELLOW
        paint.isAntiAlias = true
        bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
        bitmapCanvas = Canvas(bitmap)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.textSize = 50f
        //这里将文字画在新建的图片bitmap图层上，还需要绘制在view上面
        bitmapCanvas.drawText("你好", 0f, 100f, paint)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
    }
}