package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.wanandroid.zhangtianzhu.kotlinproject.R

class ColorView constructor(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val paint1 = Paint()
    private val paint2 = Paint()
    private val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.iv_eye_3)

    init {
        paint1.isAntiAlias = true
        paint1.setARGB(255, 200, 100, 100)
//        paint1.color = Color.GREEN
        paint2.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //绘制原始图
//        canvas.drawRect(0f,0f,300f,200f,paint1)
        canvas.drawBitmap(bitmap, null, Rect(0, 0, 500, 500 * bitmap.height / bitmap.width), paint1)

        canvas.translate(510f, 0f)

        //色彩变换矩阵，只显示蓝色值，把红色与绿色去掉，称为蓝色通道
        //色彩平移运算，也就是色彩加法运算，就是色彩变换矩阵最后一行加上某个值，可以更改色彩饱和度
        val colorMatrix = ColorMatrix(
            floatArrayOf(
                1f, 0f, 0f, 0f, 0f,
                //给绿色增加50饱和度，图片是由某个像素组成，都应用矩阵转换，导致图片每个像素绿色值都增加50饱和度
                0f, 1f, 0f, 0f, 50f,
                0f, 0f, 1f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )

        //色彩反转，反相
        val colorMatrix1 = ColorMatrix(
            floatArrayOf(
                -1f, 0f, 0f, 0f, 255f,
                0f, -1f, 0f, 0f, 255f,
                0f, 0f, -1f, 0f, 255f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        //色彩缩放，也就是色彩矩阵乘法，RGBA对角线分别乘以特定值，下面同时缩放，将亮度调整为正常1.2倍
        val colorScale = ColorMatrix(
            floatArrayOf(
                1.2f, 0f, 0f, 0f, 0f,
                0f, 1.2f, 0f, 0f, 50f,
                0f, 0f, 1.2f, 0f, 0f,
                0f, 0f, 0f, 1.2f, 0f
            )
        )

        //色彩变成黑白图片
        val colorWhiteBlack = ColorMatrix(
            floatArrayOf(
                0.213f, 0.715f, 0.072f, 0f, 0f,
                0.213f, 0.715f, 0.072f, 0f, 0f,
                0.213f, 0.715f, 0.072f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )

        //颜色反转，将红色与绿色反转
        val colorReverse = ColorMatrix(
            floatArrayOf(
                0f, 1f, 0f, 0f, 0f,
                1f, 0f, 0f, 0f, 0f,
                0f, 0f, 1f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )

        //照片变旧
        val colorLast = ColorMatrix(
            floatArrayOf(
                1 / 2f, 1 / 2f, 1 / 2f, 0f, 0f,
                1 / 3f, 1 / 3f, 1 / 3f, 0f, 0f,
                1 / 4f, 1 / 4f, 1 / 4f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        paint2.colorFilter = ColorMatrixColorFilter(colorLast)
//        canvas.drawRect(0f,0f,300f,200f,paint2)
        canvas.drawBitmap(bitmap, null, Rect(0, 0, 500, 500 * bitmap.height / bitmap.width), paint2)
    }
}