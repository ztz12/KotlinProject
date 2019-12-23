package com.wanandroid.zhangtianzhu.kotlinproject.utils

import androidx.recyclerview.widget.RecyclerView

class CustomLayoutManager : RecyclerView.LayoutManager() {
    private var mTotalHeight = 0
    //保存所有移动的dy
    private var mSumDy = 0

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        //在此方法中修改列表item布局参数
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    /**
     * 所有item的布局都在onLayoutChildren方法中进行处理，测量所有item，并进行布局操作
     */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        //定义竖直方向偏移量
        var offsetY = 0
        for (i in 0 until itemCount) {
            //将所有的view添加进来
            val view = recycler.getViewForPosition(i)
            addView(view)
            //将view摆放到它应在的位置
            measureChildWithMargins(view, 0, 0)
            //获取当前item的宽度与decoration的宽度之和
            val width = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)
            layoutDecorated(view, 0, offsetY, width, offsetY + height)
            offsetY += height
        }
        //所以取最offsetY和getVerticalSpace()的最大值是因为，offsetY是所有item的总高度，而当item填不满RecyclerView时，
        // offsetY应该是比RecyclerView的真正高度小的，而此时的真正的高度应该是RecyclerView本身所设置的高度。
        mTotalHeight = Math.max(offsetY, getVerticalSpace())
    }

    /**
     * 获取所有item的总高度
     */
    private fun getVerticalSpace(): Int {
        return height - paddingTop - paddingBottom
    }

    /**
     * 返回true表示支持垂直滑动
     */
    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        //声明一个变量表示将要移动的距离
        var travel = dy
        //判断是否滑动到顶部与底部
        //判断到顶，将偏移量相加，小于0就到顶了
        if (mSumDy + dy < 0) {
            travel = -mSumDy
        } else if (mSumDy + dy > mTotalHeight - getVerticalSpace()) {
            //mTotalHeight - getVerticalSpace() 总高度减去最后一个item高度表示滑动到底滚动的总距离
            travel = mTotalHeight - getVerticalSpace() - mSumDy
        }
        mSumDy += travel
        //平移容器中的item，向上移动要减去dy，所有直接平移负的值
        offsetChildrenVertical(-travel)
        return dy
    }

}