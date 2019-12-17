package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.wanandroid.zhangtianzhu.kotlinproject.R

class XfermodeView constructor(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val paint = Paint()

    private val bitmapWidth = 400
    private val bitmapHeight = 400
    //目标图
    private var dstBitmap: Bitmap = makeDst(bitmapWidth, bitmapHeight)
    //原图
    private var srcBitmap: Bitmap = makeSrc(bitmapWidth, bitmapHeight)

//    private val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.iv_eye_3)
//    private val bitmapWidth = 500f
//    private val bitmapHeight = bitmapWidth * bitmap.height / bitmap.width

    init {
        //关闭view层级的硬件加速，由于一些混合模式在硬件加速的条件下无法使用
//        setLayerType(LAYER_TYPE_SOFTWARE, null)
        paint.isAntiAlias = true
//        paint.color = Color.RED
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //新建图层，离屏绘制
//        val layerId = canvas.saveLayer(0f,0f,bitmapWidth,bitmapHeight,paint,Canvas.ALL_SAVE_FLAG)
        val layerId =
            canvas.saveLayer(0f, 0f, bitmapWidth * 2.toFloat(), bitmapHeight * 2.toFloat(), paint, Canvas.ALL_SAVE_FLAG)
//        canvas.drawBitmap(bitmap, null,Rect(0,0,bitmapWidth.toInt(),bitmapHeight.toInt()),paint)
        canvas.drawBitmap(dstBitmap, 0f, 0f, paint)
        //ADD 相交区域颜色饱和度进行叠加
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.ADD)
        //SRC 全部以原图像进行显示
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
        //MULTIPLY 正片叠底，让相交区域进行叠加操作，但由于原图像对应的目标图像像素为alpha 0，所有原图像不和目标图像相交的区域就变成透明的及不显示
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)
        //SRC_IN 也会显示原图像，但是当目标图像为透明不存在时，原图像的部分也会显示透明
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        //SRC_OUT 以目标图像的透明度的补值来调节源图像的透明度和色彩饱和度。即当目标图像为空白像素时，就完全显示源图像，当目标图像的透明度为100%时，交合区域为空像素。
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)

        //1、当透明度只有100%和0%时，SRC_ATOP是SRC_IN是通用的
        //2、当透明度不只有100%和0%时，SRC_ATOP相比SRC_IN源图像的饱和度会增加，即会显得更亮！
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)

        //原图像操作
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST)
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        //DST_OUT 模式下相交区域显示目标图像，当源图像透明底是100%时，则相交区域为空值。当源图像透明度为0时，则完全显示目标图像。非相交区域完全显示目标图像。
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        // 让目标图像显示在源图像上面
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
        //一般而言DST_ATOP是可以和DST_IN通用的，但DST_ATOP所产生的效果图在源图像的透明度不是0或100%的时候，会比DST_IN模式产生的图像更亮些；
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_ATOP)

//        1、DST相关模式是完全可以使用SRC对应的模式来实现的，只不过需要将目标图像和源图像对调一下即可。
//        2、在SRC模式中，是以显示源图像为主，通过目标图像的透明度来调节计算结果的透明度和饱和度，而在DST模式中，是以显示目标图像为主，
//        通过源图像的透明度来调节计算结果的透明度和饱和度。
        //清空图像 源图像所在区域都变成透明不显示
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.XOR)
        canvas.drawBitmap(srcBitmap, bitmapWidth / 2.toFloat(), bitmapHeight / 2.toFloat(), paint)
        paint.xfermode = null
        //设置混合模式
        //恢复图层
        canvas.restoreToCount(layerId)
    }

    private fun makeDst(width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.RED
        canvas.drawOval(RectF(0f, 0f, width.toFloat(), height.toFloat()), paint)
        return bitmap
    }

    private fun makeSrc(width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.GREEN
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        return bitmap
    }
}