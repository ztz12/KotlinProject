package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.wanandroid.zhangtianzhu.kotlinproject.R

class SrcOutView constructor(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val paint = Paint()
    private val srcBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.iv_eye_3)
    private val dstBitmap = Bitmap.createBitmap(srcBitmap.width, srcBitmap.height, Bitmap.Config.ARGB_8888)
    private var mPreX = 0f
    private var mPreY = 0f
    private val path = Path()

    init {
        paint.color = Color.RED
        paint.isAntiAlias = true
        paint.strokeWidth = 45f
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val layerId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        //先把手指轨迹绘制在目标bitmap上面
        val c = Canvas(dstBitmap)
        c.drawPath(path, paint)

        //然后把目标图像画到画布上面
        canvas.drawBitmap(dstBitmap, 0f, 0f, paint)
        //计算原图像区域
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
        canvas.drawBitmap(srcBitmap, 0f, 0f, paint)
        paint.xfermode = null
        canvas.restoreToCount(layerId)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(event.x, event.y)
                mPreX = event.x
                mPreY = event.y
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                val endX = (mPreX + event.x) / 2
                val endY = (mPreY + event.y) / 2
                path.quadTo(mPreX, mPreY, endX, endY)
                mPreX = event.x
                mPreY = event.y
            }
        }
        postInvalidate()
        return super.onTouchEvent(event)
    }
}