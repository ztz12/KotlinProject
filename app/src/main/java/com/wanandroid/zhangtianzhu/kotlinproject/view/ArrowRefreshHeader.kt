package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.animation.ValueAnimator
import android.content.Context
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import com.wanandroid.zhangtianzhu.kotlinproject.R
import com.wanandroid.zhangtianzhu.kotlinproject.utils.IRefreshHeader
import kotlinx.android.synthetic.main.refresh_header_item.view.*

class ArrowRefreshHeader constructor(context: Context, attributeSet: AttributeSet?) :
    LinearLayout(context, attributeSet), IRefreshHeader {
    private lateinit var mRotateUpAnimator: RotateAnimation
    private lateinit var mRotateDownAnimation: RotateAnimation
    private lateinit var mContentLayout: LinearLayout
    private val mHandler: android.os.Handler = android.os.Handler(Looper.getMainLooper())

    private var mMeasureHeight = 0
    private var mState = 0

    init {
        val layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(0, 0, 0, 0)
        this.layoutParams = layoutParams
        this.setPadding(0, 0, 0, 0)
        mContentLayout = LayoutInflater.from(context).inflate(R.layout.refresh_header_item, null) as LinearLayout
        addView(mContentLayout, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0))
        mRotateUpAnimator = RotateAnimation(
            0f, -180.0f, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        mRotateUpAnimator.duration = 200
        //动画终止时停留在最后一帧，即停止的时候是否在终点位置显示
        mRotateUpAnimator.fillAfter = true
        //动画停止时是否显示第一帧，即停止在开始位置进行显示
//        mRotateUpAnimator.fillBefore = true
        //  type是后面value的类型，有两个值：Animation.RELATIVE_TO_SELF：value的值是相对于控件自身来测量的值
        //    Animation.RELATIVE_TO_PARENT：value的值是相对于父布局来测量的。pivotXValue是旋转点坐标
        mRotateDownAnimation = RotateAnimation(
            -180.0f, 0f, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        mRotateDownAnimation.duration = 200
        mRotateDownAnimation.fillAfter = true
        //将mContentLayout 设置成自动包裹并重新测量
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        //获取测量后的高度
        mMeasureHeight = measuredHeight
    }

    override fun onReset() {
        setState(STATE_NORMAL)
    }

    override fun onPrepare() {
        setState(STATE_RELEASE_TO_REFRESH)
    }

    override fun onMove(offset: Float, sumOffset: Float) {
        if (getVisibleHeight() > 0 || offset > 0) {
            setVisibleHeight(offset + getVisibleHeight())
            //未处于刷新状态，更新箭头
            if (mState <= STATE_RELEASE_TO_REFRESH) {
                if (getVisibleHeight() > mMeasureHeight) {
                    onPrepare()
                } else {
                    onReset()
                }
            }
        }
    }

    override fun onRelease(): Boolean {
        var isOnRefresh = false
        val height = getVisibleHeight()
        // not visible.
        if (height == 0) {
            isOnRefresh = false
        }

        if (getVisibleHeight() > mMeasureHeight && mState < STATE_REFRESHING) {
            setState(STATE_REFRESHING)
            isOnRefresh = true
        }
        // refreshing and header isn't shown fully. do nothing.
        if (mState == STATE_REFRESHING && height > mMeasureHeight) {
            smoothScrollTo(mMeasureHeight.toFloat())
        }
        if (mState != STATE_REFRESHING) {
            smoothScrollTo(0f)
        }

        if (mState == STATE_REFRESHING) {
            val destHeight = mMeasureHeight
            smoothScrollTo(destHeight.toFloat())
        }

        return isOnRefresh
    }

    override fun onRefreshComplete() {
        //设置刷新状态已经完成
        setState(STATE_DOWN)
        //200ms后重置
        mHandler.postDelayed({
            reset()
        }, 200)
    }

    private fun reset() {
        smoothScrollTo(0f)
        setState(STATE_NORMAL)
    }

    override fun getHeadView(): View {
        return this
    }

    override fun getVisibleHeight(): Int {
        val lp = mContentLayout.layoutParams
        return lp.height
    }

    private fun setState(state: Int) {
        if (state == mState) return
        when (state) {
            STATE_NORMAL -> {
                if (mState == STATE_RELEASE_TO_REFRESH) {
                    ivHeaderArrow.startAnimation(mRotateDownAnimation)
                }
                if (mState == STATE_REFRESHING) {
                    ivHeaderArrow.clearAnimation()
                }
                tvRefreshStatus.text = "下拉刷新"
            }
            STATE_RELEASE_TO_REFRESH -> {
                ivHeaderArrow.visibility = View.VISIBLE
                refreshProgress.visibility = View.INVISIBLE
                if (mState != STATE_RELEASE_TO_REFRESH) {
                    ivHeaderArrow.clearAnimation()
                    ivHeaderArrow.startAnimation(mRotateUpAnimator)
                    tvRefreshStatus.text = "释放立即刷新"
                }
            }
            STATE_REFRESHING -> {
                ivHeaderArrow.clearAnimation()
                ivHeaderArrow.visibility = View.INVISIBLE
                refreshProgress.visibility = View.VISIBLE
                smoothScrollTo(mMeasureHeight.toFloat())
                tvRefreshStatus.text = "正在刷新..."
            }
            STATE_DOWN -> {
                ivHeaderArrow.visibility = View.INVISIBLE
                refreshProgress.visibility = View.INVISIBLE
                tvRefreshStatus.text = "刷新完成"
            }
        }
        //保存当前状态
        mState = state
    }

    /**
     * 下拉之后，当正在刷新，从下拉位置恢复到规定位置的动画
     */
    private fun smoothScrollTo(destHeight: Float) {
        val valueAnimation = ValueAnimator.ofFloat(getVisibleHeight().toFloat(), destHeight)
        valueAnimation.duration = 300
        valueAnimation.run {
            addUpdateListener { animation -> setVisibleHeight(animation.animatedValue as Float) }
            start()
        }
    }

    /**
     * 设置刷新头部可见高度
     */
    private fun setVisibleHeight(height: Float) {
        var height = height
        if (height < 0) {
            height = 0F
        }
        val lp: ViewGroup.LayoutParams = mContentLayout.layoutParams
        lp.height = height.toInt()
        mContentLayout.layoutParams = lp
    }
}