package com.wanandroid.zhangtianzhu.kotlinproject.utils

import android.graphics.Rect
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GalleryLayoutManager : RecyclerView.LayoutManager() {
    private var mSumDx = 0
    private var mTotalWidth = 0

    private var mItemWidth: Int = 0
    private var mItemHeight: Int = 0
    private val mItemRects = SparseArray<Rect>()
    private var mSatrtX = 0

    /**
     * 申请一个变量保存两个卡片之间的距离
     */
    private var mItemIntervalWidth = 0
    /**
     * 记录Item是否出现过屏幕且还没有回收。true表示出现过屏幕上，并且还没被回收
     */
    private val mHasAttachedItems = SparseBooleanArray()

    private val horizontalSpace: Int
        get() = width - paddingLeft - paddingRight

    /**
     * 获取可见的区域Rect
     *
     * @return
     */
    private val visibleArea: Rect
        get() = Rect(paddingLeft + mSumDx, paddingTop, width - paddingRight + mSumDx, height - paddingBottom)

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        //在此方法中修改列表item布局参数
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (itemCount == 0) {//没有Item，界面空着吧
            detachAndScrapAttachedViews(recycler)
            return
        }
        mHasAttachedItems.clear()
        mItemRects.clear()

        detachAndScrapAttachedViews(recycler)

        //将item的位置存储起来
        val childView = recycler.getViewForPosition(0)
        measureChildWithMargins(childView, 0, 0)
        mItemWidth = getDecoratedMeasuredWidth(childView)
        mItemHeight = getDecoratedMeasuredHeight(childView)

        //在初始化的时候，计算卡片的起始位置，但是卡片的宽高没有变化
        mItemIntervalWidth = getInterValWidth()

        //在初始化的时候第一个卡片在中间，设置开始位移
        mSatrtX = width / 2 - mItemIntervalWidth

        //定义竖直方向的偏移量，位移的变量不加上之前卡片的整个宽度，加上一半的宽度，实现卡片叠加效果
        var offsetX = 0

        for (i in 0 until itemCount) {
            val rect = Rect(offsetX + mSatrtX, 0, mSatrtX + mItemWidth + offsetX, mItemHeight)
            mItemRects.put(i, rect)
            mHasAttachedItems.put(i, false)
            offsetX += mItemIntervalWidth
        }


        //获取一个屏幕最多方向多少个item，可见的情况下
        val visibleCount = horizontalSpace / mItemIntervalWidth
        val rect = visibleArea
        for (i in 0 until visibleCount) {
            insertView(i, rect, recycler, false)
        }

        //如果所有子View的高度和没有填满RecyclerView的高度，
        // 则将高度设置为RecyclerView的高度
        mTotalWidth = Math.max(offsetX, horizontalSpace)
    }

    //实现横向布局
    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State?): Int {
        if (childCount <= 0) {
            return dx
        }

        var travel = dx
        //如果滑动到最顶部
        if (mSumDx + dx < 0) {
            travel = -mSumDx
        } else if (mSumDx + dx > getMaxOffset()) {
            //如果滑动到最右边
            travel = getMaxOffset() - mSumDx
        }

        mSumDx += travel

        val visibleRect = visibleArea

        //回收越界子View
        for (i in childCount - 1 downTo 0) {
            val child = getChildAt(i)
            val position = getPosition(child!!)
            val rect = mItemRects.get(position)

            if (!Rect.intersects(rect, visibleRect)) {
                removeAndRecycleView(child, recycler)
                mHasAttachedItems.put(position, false)
            } else {
                layoutDecoratedWithMargins(child, rect.left - mSumDx, rect.top, rect.right - mSumDx, rect.bottom)
//                child.rotationY = child.rotationY + 1
                handleChildScale(child, rect.left - mSumDx - mSatrtX.toFloat())
                mHasAttachedItems.put(position, true)
            }
        }

        val lastView = getChildAt(childCount - 1)
        val firstView = getChildAt(0)
        if (travel >= 0) {
            val minPos = getPosition(firstView!!)
            for (i in minPos until itemCount) {
                insertView(i, visibleRect, recycler, false)
            }
        } else {
            val maxPos = getPosition(lastView!!)
            for (i in maxPos downTo 0) {
                insertView(i, visibleRect, recycler, true)
            }
        }
        return travel
    }

    private fun insertView(pos: Int, visibleRect: Rect, recycler: RecyclerView.Recycler, firstPos: Boolean) {
        val rect = mItemRects.get(pos)
        if (Rect.intersects(visibleRect, rect) && !mHasAttachedItems.get(pos)) {
            val child = recycler.getViewForPosition(pos)
            if (firstPos) {
                addView(child, 0)
            } else {
                addView(child)
            }
            measureChildWithMargins(child, 0, 0)
            layoutDecoratedWithMargins(child, rect.left - mSumDx, rect.top, rect.right - mSumDx, rect.bottom)
            handleChildScale(child, rect.left - mSumDx - mSatrtX.toFloat())
            //在布局item后，修改每个item的旋转度数
//            child.rotationY = child.rotationY + 1
            mHasAttachedItems.put(pos, true)
        }
    }

    private fun getInterValWidth(): Int {
        return mItemWidth / 2
    }

    /**
     * 获得中心item
     */
    fun getCenterPos(): Int {
        //根据移动距离得出移动多少个item
        var pos = mSumDx / getInterValWidth()
        //表示正在移动的item移动过的距离，如果大于半个item，就让pos++，让下一个item成为中心位置，让它最后绘制，显示在上层
        val more = mSumDx % getInterValWidth()
        if (more > getInterValWidth() * 0.5) pos++
        return pos
    }

    /**
     * 得到第一个可见item的位置
     */
    fun getFirstVisiblePos(): Int {
        if (childCount <= 0) return 0
        val childView = getChildAt(0)
        return getPosition(childView!!)
    }

    private fun handleChildScale(childView: View, moveX: Float) {
        val radio = computeScale(moveX)
        val rotation = computeRotationY(moveX.toInt())
        childView.scaleX = radio
        childView.scaleY = radio
        childView.rotationY = rotation
    }

    /**
     * 计算一个缩放值
     */
    private fun computeScale(x: Float): Float {
        var scale = 1 - Math.abs(x * 1.0f / (8f * getInterValWidth()))
        if (scale <= 0) scale = 0f
        if (scale > 1) scale = 1f
        return scale
    }

    /**
     * 获取最大的滚动距离
     */
    private fun getMaxOffset(): Int {
        return (itemCount - 1) * getInterValWidth()
    }


    /**
     * 进行向左与右校正代码
     */
    fun calculateDistance(velocityX: Int, distance: Double): Double {
        val extra = mSumDx % getInterValWidth()
        val realDistance: Double
        //表示向右滑动
        realDistance = if (velocityX > 0) {
            if (distance < getInterValWidth()) {
                (getInterValWidth() - extra).toDouble()
            } else {
                distance - distance % getInterValWidth() - extra.toDouble()
            }
            //表示向左滑动
        } else {
            if (distance < getInterValWidth()) {
                extra.toDouble()
            } else {
                distance - distance % getInterValWidth() + extra
            }
        }
        return realDistance
    }

    /**
     * 最大Y轴旋转度数
     */
    private val M_MAX_ROTATION_Y = 30.0f

    private fun computeRotationY(x: Int): Float {
        var rotationY: Float
        rotationY = -M_MAX_ROTATION_Y * x / getInterValWidth()
        if (Math.abs(rotationY) > M_MAX_ROTATION_Y) {
            if (rotationY > 0) {
                rotationY = M_MAX_ROTATION_Y
            } else {
                rotationY = -M_MAX_ROTATION_Y
            }
        }
        return rotationY
    }
}
