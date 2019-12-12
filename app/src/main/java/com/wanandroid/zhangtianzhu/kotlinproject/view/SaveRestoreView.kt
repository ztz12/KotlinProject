package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class SaveRestoreView constructor(context: Context,attributeSet: AttributeSet):View(context,attributeSet) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        //画布裁剪 clip
//        canvas.drawColor(Color.RED)
//        //保存当前画布即整个屏幕
//        canvas.save()
//        canvas.clipRect(Rect(10,10,30,50))
//        canvas.drawColor(Color.BLUE)
//
//        //恢复之前保存的画布
//        canvas.restore()
//        canvas.drawColor(Color.GREEN)

        //多次调用save与restore方法
        canvas.drawColor(Color.RED)
        //保存全屏画布
        canvas.save()

        canvas.clipRect(Rect(100,100,800,800))
        canvas.drawColor(Color.BLUE)
        //保存当前矩形Rect(100,100,800,800)区域的画布
        canvas.save()

        canvas.clipRect(Rect(200,200,700,700))
        canvas.drawColor(Color.GREEN)
        //保存当前矩形Rect(200,200,700,700)区域画布
        canvas.save()

        canvas.clipRect(Rect(300,300,600,600))
        canvas.drawColor(Color.YELLOW)
        //保存当前矩形Rect(300,300,600,600)区域画布
        canvas.save()

        canvas.clipRect(Rect(400,400,500,500))
        canvas.drawColor(Color.WHITE)

        //把此时的栈顶画布取出来，也就是当前矩形Rect(300,300,600,600)区域画布
//        canvas.restore()
//        canvas.drawColor(Color.BLACK)

        //取三次，也就是当前矩形Rect(100,100,800,800)区域的画布
        canvas.restore()
        canvas.restore()
        canvas.restore()
        canvas.drawColor(Color.BLACK)

    }
}