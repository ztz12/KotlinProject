package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.wanandroid.zhangtianzhu.kotlinproject.R

class BitmapShaderView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val paint = Paint()
    private var bitmap: Bitmap

    init {
        paint.isAntiAlias = true
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.dog)
        //x 轴与y轴 都用重复图像填充剩余空间
//        paint.shader = BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        //使用镜像图像填充剩余空间
//        paint.shader = BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR)
        //使用图像的边缘颜色填充剩余空间
//        paint.shader = BitmapShader(bitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP)

        //bitmapShader 着色器，给空白图像进行着色，并设置图像填空剩余空间，总是先从控件的左上角开始填充
        //x轴使用重读图像，y轴使用镜像填充
        //是先填充y轴，再填充x轴
        paint.shader = BitmapShader(bitmap,Shader.TileMode.REPEAT,Shader.TileMode.MIRROR)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //用于获取控件的宽高
        //这里绘制区域只是整个图像的一小部分，无论利用绘图绘制多大一块，在那绘制，都与Shader无关，shader总是从左上角开始，而你绘制的部分只是显示出来的部分，
        //没有显示出来的部分已经生成，只是没有显示而已
        canvas.drawRect(100f, 20f, 200f,200f, paint)
    }
}