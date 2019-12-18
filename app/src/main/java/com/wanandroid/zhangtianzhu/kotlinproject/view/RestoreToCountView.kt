package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class RestoreToCountView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val TAG = "RestoreToCountView"
    private val paint = Paint()

    init {
        paint.color = Color.BLUE
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val id1 = canvas.save()

        canvas.clipRect(0f, 0f, 800f, 800f)
        canvas.drawColor(Color.YELLOW)
        Log.d(TAG, "count= " + canvas.saveCount + " id1= " + id1)

        val id2 = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), paint, Canvas.ALL_SAVE_FLAG)
        canvas.clipRect(100f, 100f, 700f, 700f)
        canvas.drawColor(Color.GREEN)
        Log.d(TAG, "count= " + canvas.saveCount + " id2= " + id2)

        val id3 = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), paint, Canvas.ALL_SAVE_FLAG)
        canvas.clipRect(200f, 200f, 600f, 600f)
        canvas.drawColor(Color.BLACK)
        Log.d(TAG, "count= " + canvas.saveCount + " id3= " + id3)

        val id4 = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), paint, Canvas.ALL_SAVE_FLAG)
        canvas.clipRect(300f, 300f, 500f, 500f)
        canvas.drawColor(Color.RED)
        Log.d(TAG, "count= " + canvas.saveCount + " id4= " + id4)

        //恢复id3 之前的画布，也就是100f,100f,700f,700f
//        canvas.restoreToCount(id3)
        //恢复200f, 200f, 600f, 600f画布
        canvas.restore()
        canvas.drawColor(Color.GRAY)
        Log.d(TAG, "count= " + canvas.saveCount)
    }
}