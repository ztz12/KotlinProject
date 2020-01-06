package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class ShimmerTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatTextView(context, attrs, defStyleAttr) {
    private var mPaint: Paint? = null
    private var linearGradient: LinearGradient? = null
    private var mDx = 0

    init {
        init()
    }

    private fun init() {
        //获取textView自带的画笔
        mPaint = paint
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        //从起止位置与终止位置看出渐变运动长度为文本的两倍长度大小
        val valueAnimator = ValueAnimator.ofInt(0, 2 * measuredWidth)
        valueAnimator.addUpdateListener { valueAnimator ->
            mDx = valueAnimator.animatedValue as Int
            postInvalidate()
        }
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.repeatMode = ValueAnimator.REVERSE
        valueAnimator.duration = 600
        valueAnimator.start()

        //getCurrentTextColor TextView自带方法获取文字颜色，初始位置为文字的左侧，填充模式使用边缘填充
        linearGradient = LinearGradient(
            (-measuredWidth).toFloat(),
            0f,
            0f,
            0f,
            intArrayOf(currentTextColor, -0xff0100, currentTextColor),
            floatArrayOf(0f, 0.5f, 1f),
            Shader.TileMode.CLAMP
        )

    }

    override fun onDraw(canvas: Canvas) {
        val matrix = Matrix()
        //通过矩阵来设置渐变位移
        matrix.setTranslate(mDx.toFloat(), 0f)
        linearGradient!!.setLocalMatrix(matrix)
        mPaint!!.shader = linearGradient
        super.onDraw(canvas)
    }
}
