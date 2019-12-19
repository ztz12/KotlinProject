package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.wanandroid.zhangtianzhu.kotlinproject.R

class AvatarView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val paint = Paint()
    private var mBitmapShader: BitmapShader
    private var mBitmap: Bitmap
    private var mMatrix: Matrix

    init {
        paint.isAntiAlias = true
        val options = BitmapFactory.Options()
        options.inSampleSize = 2
        mBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.avator_shader, options)
        //在初始化时创建一个BitmapShader，填充模式分别是TileMode.CLAMP、TileMode.CLAMP，其实这里填充模式没什么用，
        // 因为我们只需要显示当前图片；所以不存在多余空白区域，所以使用哪种填充模式都无所谓。
        mBitmapShader = BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        mMatrix = Matrix()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val scale = width / mBitmap.width.toFloat()
        mMatrix.setScale(scale, scale)
        mBitmapShader.setLocalMatrix(mMatrix)
        paint.shader = mBitmapShader
        val half = width / 2.toFloat()
        canvas.drawCircle(half, half, half, paint)
    }
}