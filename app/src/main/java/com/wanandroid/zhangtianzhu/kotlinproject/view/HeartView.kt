package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.wanandroid.zhangtianzhu.kotlinproject.R

class HeartView constructor(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val opt = BitmapFactory.Options()

    private var dstBitmap: Bitmap

    private var srcBitmap: Bitmap

    private var dx = 0
    private var mItemLength = 0

    private val paint = Paint()
    private var c: Canvas
    private var mXfermode: PorterDuffXfermode

    init {
        paint.color = Color.RED
        opt.inSampleSize = 2
        dstBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.heart, opt)
        srcBitmap = Bitmap.createBitmap(dstBitmap.width, dstBitmap.height, Bitmap.Config.ARGB_8888)
        c = Canvas(srcBitmap)
        mXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        mItemLength = dstBitmap.width
        startAnimation()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //清空bitmap
        c.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR)
        //画上矩形
        c.drawRect((dstBitmap.width - dx).toFloat(), 0f, dstBitmap.width.toFloat(), dstBitmap.height.toFloat(), paint)
        //模式合成
        val layerId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        canvas.drawBitmap(dstBitmap, 0f, 0f, paint)
        paint.xfermode = mXfermode
        canvas.drawBitmap(srcBitmap, 0f, 0f, paint)
        paint.xfermode = null
        canvas.restoreToCount(layerId)
    }

    private fun startAnimation() {
        val valueAnimator = ValueAnimator.ofInt(0, mItemLength)
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.duration = 4500
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.addUpdateListener { valueAnimator ->
            dx = valueAnimator.animatedValue as Int
            postInvalidate()
        }
        valueAnimator.start()
    }
}