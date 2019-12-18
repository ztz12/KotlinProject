package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.wanandroid.zhangtianzhu.kotlinproject.R

class ShaderView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val paint = Paint()
    private val option = BitmapFactory.Options()
    private val bitmap:Bitmap
    private var mRadius = 1f
    private var mDx = 10f
    private var mDy = 10f
    private var mSetShader = true

    init {
        paint.isAntiAlias = true
        paint.color = Color.GREEN
        paint.textSize = 35f
        //关闭view的硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        option.inSampleSize = 2
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.iv_eye_4,option)
    }

    fun changeRadius() {
        mRadius++
        postInvalidate()
    }

    fun changeDx() {
        mDx++
        postInvalidate()
    }

    fun changeDy() {
        mDy++
        postInvalidate()
    }

    fun clearShader(){
        mSetShader = false
        postInvalidate()
    }

    fun setShader(){
        mSetShader = true
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //setShadowLayer 只有文字绘制时支持硬件加速，其余不支持
        if(mSetShader) {
            paint.setShadowLayer(mRadius, mDx, mDy, Color.GRAY)
        }else{
            //清除阴影
            paint.clearShadowLayer()
        }
        canvas.drawText("Yif", 100f, 100f, paint)
        canvas.drawCircle(200f, 200f, 50f, paint)

        canvas.drawBitmap(bitmap, null, Rect(200, 300, 200 + bitmap.width, 300 + bitmap.height), paint)

    }
}