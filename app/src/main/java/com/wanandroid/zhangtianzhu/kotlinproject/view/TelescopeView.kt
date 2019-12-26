package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.wanandroid.zhangtianzhu.kotlinproject.R

class TelescopeView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val paint = Paint()
    private var mDx = -1f
    private var mDy = -1f
    private var mBgBitmap: Bitmap? = null
    private var mBitmap: Bitmap

    init {
        paint.isAntiAlias = true
        mBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.iv_eye_33)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mDx = event.x
                mDy = event.y
                postInvalidate()
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                mDx = event.x
                mDy = event.y
            }
            MotionEvent.ACTION_UP -> {
                mDx = -1f
                mDy = -1f
            }

            MotionEvent.ACTION_CANCEL -> {
                mDx = -1f
                mDy = -1f
            }
        }
        postInvalidate()
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //创建与图像一样大小的背景
        if (mBgBitmap == null) {
            mBgBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            //以bitmap为底新建一个画布，把mBitmap画在这个画布上面，还需要将mBgBitmap画在view上面才能显示
            val canvasBitmap = Canvas(mBgBitmap)
            //将当前bitmap画在当前矩形区域内 src为空，就是原来的mBitmap 显示整个图片,dst是裁剪后的图片在canvas画布上显示的区域
            //如果src不为null的话，按src截取subBitmap，并将subBitmap通过自动缩放／平移去适应目标矩形(dst)；如果src为null的画，subBitmap就是原bitmap
            canvasBitmap.drawBitmap(mBitmap, null, Rect(0, 0, width, height), paint)
        }

        //利用motionEvent捕捉手指的位置，进行画一个圆将图像在空白区域进行重复填充显示出来
        if (mDx != -1f && mDy != -1f) {
            paint.shader = BitmapShader(mBgBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
            canvas.drawCircle(mDx, mDy, 150f, paint)
        }
    }
}