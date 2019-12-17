package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.wanandroid.zhangtianzhu.kotlinproject.R

class SaveLayerUseExampleView(context: Context,attributeSet: AttributeSet):View(context,attributeSet) {
    private val paint = Paint()
    private var bitmap:Bitmap
    init {
        paint.color = Color.RED
        paint.isAntiAlias = true
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.iv_eye_4)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap,0f,0f,paint)
        //新建画布，原画布不受影响 ALL_SAVE_FLAG保存所有内容
        val layerId = canvas.saveLayer(0f,0f,width.toFloat(),height.toFloat(),paint,Canvas.ALL_SAVE_FLAG)
        //将新的画布错切
        canvas.skew(0f,1.177f)
        canvas.drawRect(0f,0f,100f,100f,paint)
        canvas.restoreToCount(layerId)
    }
}