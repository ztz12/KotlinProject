package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.wanandroid.zhangtianzhu.kotlinproject.R

/**
 * QQ 粘性小红点
 */
class RedPointControlVIew(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var mStartPoint: PointF = PointF(100f, 100f)
    private val mCurPoint: PointF = PointF()
    private val DEFAULT_RADIUS = 20f
    private var mRadius = DEFAULT_RADIUS
    private val mPaint = Paint()
    private val mPath = Path()
    private var mTouch = false
    private var isAnimStart = false
    private val mTipTextView: TextView = TextView(context)
    private val exploredImageView: ImageView = ImageView(context)

    init {
        mPaint.color = Color.RED
        mPaint.style = Paint.Style.FILL

        val params = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mTipTextView.layoutParams = params
        mTipTextView.setPadding(10, 10, 10, 10)
        mTipTextView.setBackgroundResource(R.drawable.tv_bg)
        mTipTextView.setTextColor(Color.WHITE)
        mTipTextView.text = "99+"

        exploredImageView.layoutParams = params
        exploredImageView.setImageResource(R.drawable.tip_anim)
        exploredImageView.visibility = View.INVISIBLE

        addView(mTipTextView)
        addView(exploredImageView)
    }

    private fun calculatePath() {

        val x = mCurPoint.x
        val y = mCurPoint.y
        val startX = mStartPoint.x
        val startY = mStartPoint.y
        val dx = x - startX
        val dy = y - startY
        val a = Math.atan((dy / dx).toDouble())
        val offsetX = (mRadius * Math.sin(a)).toFloat()
        val offsetY = (mRadius * Math.cos(a)).toFloat()

        val distance =
            Math.sqrt(Math.pow((y - startY).toDouble(), 2.0) + Math.pow((x - startX).toDouble(), 2.0)).toFloat()
        mRadius = -distance / 40 + DEFAULT_RADIUS
        if (mRadius < 9) {
            isAnimStart = true
            exploredImageView.x = mCurPoint.x - mTipTextView.width / 2
            exploredImageView.y = mCurPoint.y - mTipTextView.height / 2
            exploredImageView.visibility = View.VISIBLE
            (exploredImageView.drawable as AnimationDrawable).start()

            mTipTextView.visibility = View.GONE
        }

        // 根据角度算出四边形的四个点
        val x1 = startX + offsetX
        val y1 = startY - offsetY

        val x2 = x + offsetX
        val y2 = y - offsetY

        val x3 = x - offsetX
        val y3 = y + offsetY

        val x4 = startX - offsetX
        val y4 = startY + offsetY

        val anchorX = (startX + x) / 2
        val anchorY = (startY + y) / 2

        //将p1-p2与p3-p4用贝塞尔曲线连接，p1-p4与p2-p3用直线连接
        mPath.reset()
        mPath.moveTo(x1, y1)
        mPath.quadTo(anchorX, anchorY, x2, y2)
        mPath.lineTo(x3, y3)
        mPath.quadTo(anchorX, anchorY, x4, y4)
        mPath.lineTo(x1, y1)
    }

    /**
     * onDraw:为什么要行绘制自己的,然后再调用super.onDraw
     * @param canvas
     */
    override fun dispatchDraw(canvas: Canvas) {

        canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), mPaint, Canvas.ALL_SAVE_FLAG)

        if (!mTouch || isAnimStart) {
            mTipTextView.x = mStartPoint.x - mTipTextView.width / 2
            mTipTextView.y = mStartPoint.y - mTipTextView.height / 2
        } else {
            calculatePath()
            canvas.drawPath(mPath, mPaint)
            canvas.drawCircle(mStartPoint.x, mStartPoint.y, mRadius, mPaint)
            canvas.drawCircle(mCurPoint.x, mCurPoint.y, mRadius, mPaint)

            //将textview的中心放在当前手指位置
            mTipTextView.x = mCurPoint.x - mTipTextView.width / 2
            mTipTextView.y = mCurPoint.y - mTipTextView.height / 2
        }
        canvas.restore()

        //super 放下面，是先绘制自己，再绘制子view，否则子view会被遮住
        //该方法是绘制所有的子控件
        super.dispatchDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 判断触摸点是否在tipImageView中
                val rect = Rect()
                val location = IntArray(2)
                //获取文本的屏幕坐标
                mTipTextView.getLocationOnScreen(location)
                rect.left = location[0]
                rect.top = location[1]
                rect.right = mTipTextView.width + location[0]
                rect.bottom = mTipTextView.height + location[1]
                if (rect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    mTouch = true
                }
            }
            MotionEvent.ACTION_UP -> {
                //抬起手指时还原位置
                mTouch = false
            }
        }

        postInvalidate()
        mCurPoint.set(event.x, event.y)
        return true
    }
}
