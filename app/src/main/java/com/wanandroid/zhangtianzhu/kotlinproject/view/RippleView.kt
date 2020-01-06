package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.widget.AppCompatButton

/**
 * 按钮水波纹效果
 */
class RippleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatButton(context, attrs, defStyleAttr) {
    private var paint: Paint? = null
    private var mDx: Float = 0.toFloat()
    private var mDy: Float = 0.toFloat()
    private val DEFAULT_RADIUS = 50
    private var objectAnimator: ObjectAnimator? = null
    private var mCurRadius = 0

    init {
        init()
    }

    private fun init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        paint = Paint()
        paint!!.isAntiAlias = true
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (mDx != event.x && mDy != event.y) {
            mDx = event.x
            mDy = event.y
            setRadius(DEFAULT_RADIUS)
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                return true
            }
            MotionEvent.ACTION_UP -> {
                if (objectAnimator != null && objectAnimator!!.isRunning) {
                    objectAnimator!!.cancel()
                }

                if (objectAnimator == null) {
                    objectAnimator = ObjectAnimator.ofInt(this, "radius", DEFAULT_RADIUS, width)
                }
                objectAnimator!!.interpolator = AccelerateInterpolator()
                objectAnimator!!.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        //动画结束让渐变半径为0
                        setRadius(0)
                    }
                })
                objectAnimator!!.start()
            }

        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(mDx, mDy, mCurRadius.toFloat(), paint!!)
    }

    private fun setRadius(radius: Int) {
        mCurRadius = radius
        if (mCurRadius > 0) {
            //设置初始颜色透明度为0，显示出按钮的颜色，结束颜色为纯天空蓝颜色，空余部分使用CLAMP实现水波纹效果
            val radialGradient =
                RadialGradient(mDx, mDy, mCurRadius.toFloat(), 0x00FFFFFF, -0xa70554, Shader.TileMode.CLAMP)
            paint!!.shader = radialGradient
        }
        postInvalidate()
    }
}
