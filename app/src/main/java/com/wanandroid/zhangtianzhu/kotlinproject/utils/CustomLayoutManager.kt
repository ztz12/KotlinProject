package com.wanandroid.zhangtianzhu.kotlinproject.utils

import android.graphics.Rect
import android.util.SparseArray
import android.util.SparseBooleanArray
import androidx.recyclerview.widget.RecyclerView

class CustomLayoutManager : RecyclerView.LayoutManager() {
    private var mTotalHeight = 0
    //保存所有移动的dy
    private var mSumDy = 0
    private val mItemsRect = SparseArray<Rect>()
    //保存那些item 已经布局好了
    private val mHasAttachedItems = SparseBooleanArray()
    private var mItemWidth = 0
    private var mItemHeight = 0

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        //在此方法中修改列表item布局参数
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    /**
     * 所有item的布局都在onLayoutChildren方法中进行处理，测量所有item，并进行布局操作
     */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        //没有item，界面空着，不处理
        if (itemCount == 0) {
            //没有数据，直接剥离，屏幕清空
            detachAndScrapAttachedViews(recycler)
            return
        }
        mItemsRect.clear()
        mHasAttachedItems.clear()
        //剥离列表中的item，存储在mAttachedScrap中，以便在新的位置添加，重新布局，不参与回收
        detachAndScrapAttachedViews(recycler)
        //getViewForPosition用于向列表申请一个holderview，这个vie从哪个池子中获取无需关心
//        它会先在mAttachedScrap中找，看要的View是不是刚刚剥离的，如果是就直接返回使用，如果不是，先在mCachedViews中查找，
//        因为在mCachedViews中精确匹配，如果匹配到，就说明这个HolderView是刚刚被移除的，也直接返回，如果匹配不到就会最终到mRecyclerPool找，
//        如果mRecyclerPool有现成的holderView实例，这时候就不再是精确匹配了，只要有现成的holderView实例就返回给我们使用，只有在mRecyclerPool为空时，
//        才会调用onCreateViewHolder新建。

        //将item存储起来
        val childView = recycler.getViewForPosition(0)
        measureChildWithMargins(childView, 0, 0)
        mItemWidth = getDecoratedMeasuredWidth(childView)
        mItemHeight = getDecoratedMeasuredHeight(childView)

        //获取一屏幕能够放下多少个item，不重复创建
        val visibleCount = getVerticalSpace() / mItemHeight
        //定义竖直方向偏移量
        var offsetY = 0
        //遍历所有的item，将它们存储到SparseArray中
        for (i in 0 until itemCount) {
            val rect = Rect(0, offsetY, mItemWidth, offsetY + mItemHeight)
            mItemsRect.put(i, rect)
            //先清空，遍历所以item，设置为false，表示没有被重新布局
            mHasAttachedItems.put(i, false)
            offsetY += mItemHeight
        }

        //然后将可见的item显示出来，不可见的不进行显示
        for (i in 0 until visibleCount) {
            //由于之前进行了剥离操作，必须重新addView进来，进行测量，才能获取对应的宽高
            val rect = mItemsRect[i]
            val view = recycler.getViewForPosition(i)
            addView(view)
            //addView后一定要measure，先measure再layout
            measureChildWithMargins(view, 0, 0)
            layoutDecorated(view, rect.left, rect.top, rect.right, rect.bottom)
        }

//        for (i in 0 until itemCount) {
//            //将所有的view添加进来
//            val view = recycler.getViewForPosition(i)
//            addView(view)
//            //将view摆放到它应在的位置
//            measureChildWithMargins(view, 0, 0)
//            //获取当前item的宽度与decoration的宽度之和
//            val width = getDecoratedMeasuredWidth(view)
//            val height = getDecoratedMeasuredHeight(view)
//            layoutDecorated(view, 0, offsetY, width, offsetY + height)
//            offsetY += height
//        }
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
        if (childCount <= 0) return dy
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

        //处理向上滚动的回收复用
        //回收越界子view  childCount获取的是当前列表可见的item数量
//        for (i in childCount - 1 downTo 0) {
//            val view = getChildAt(i)
//            //travel>0 表示上下滑动，回收当前item，上越界的view
//            if (travel > 0) {
//                //getDecoratedBottom(view!!)-travel表示这个item上移动后，它的下边界位置小于当前可视区域
//                if (getDecoratedBottom(view!!) - travel < 0) {
//                    //这个函数仅用于滚动的时候，在滚动时，我们需要把滚出屏幕的HolderView标记为Removed，
//                    // 这个函数的作用就是把已经不需要的HolderView标记为Removed。，想必大家在理解了上面的回收复用原理以后，
//                    // 也知道在我们把它标记为Removed以后，系统做了什么事了。在我们标记为Removed以为，会把这个HolderView移到mCachedViews中，
//                    // 如果mCachedViews已满，就利用先进先出原则，将mCachedViews中老的holderView移到mRecyclerPool中，
//                    // 然后再把新的HolderView加入到mCachedViews中。
//                    removeAndRecycleView(view, recycler)
//                    continue
//                }
//            }
//            //处理向下滚动回收复用
//            else if (travel < 0) {
//                //height-paddingBottom列表显示的最低位置，getDecoratedTop(view!!)-travel移动travel距离后，这个item位置，如果这个item顶部位置在屏幕下方，不可见
//                if (getDecoratedTop(view!!) - travel > height - paddingBottom) {
//                    removeAndRecycleView(view, recycler)
//                    continue
//                }
//            }
//        }

        mSumDy += travel
        val visibleRect = getVisibleArea()
        //回收越界子view 回收越界holder view时候，将已经重新布局的item设置为true，将被回收时，回收的item设置为false
        for (i in childCount - 1 downTo 0) {
            val childView = getChildAt(i)
            val position = getPosition(childView!!)
            val rect = mItemsRect[position]
            if (!Rect.intersects(visibleRect, rect)) {
                removeAndRecycleView(childView, recycler)
                mHasAttachedItems.put(position, false)
            } else {
                layoutDecoratedWithMargins(childView, rect.left, rect.top - mSumDy, rect.right, rect.bottom - mSumDy)
                childView.rotationY = childView.rotationY + 1
                mHasAttachedItems.put(position, true)
            }
        }

        //首先拿到屏幕移动后的可见区域
//        val visibleRect = getVisibleArea(travel)
        //获取列表第一个可见的item
        val firstView = getChildAt(0)
        //获取最后一个可见item
        val lastView = getChildAt(childCount - 1)
        //剥离列表item
//        detachAndScrapAttachedViews(recycler)
        //布局子view阶段
        if (travel >= 0) {
            //拿到最后可见item的后一个item getPosition是用来获取某个item view在adapter中的索引位置
            val minPos = getPosition(lastView!!) + 1
            //顺序添加子view，从这个item开始，判断从这个item开始的每个item是否都在这个可见区域，在的话，就添加进来，用从这个item到剩下的列表item
            // 来替换当前可见区域，实现向上滑动的回收复用
            //这里使用的是所有item的个数及itemCount
            for (i in minPos until itemCount) {
                insert(i, visibleRect, recycler, false)
//                val rect = mItemsRect[i]
//                if (Rect.intersects(visibleRect, rect)) {
//                    val childView = recycler.getViewForPosition(i)
//                    addView(childView)
//                    measureChildWithMargins(childView, 0, 0)
//                    //这次移动距离不包括travel，是假设已经移动，但没有真正移动，所以直接减去上次移动距离
//                    layoutDecorated(childView, rect.left, rect.top - mSumDy, rect.right, rect.bottom - mSumDy)
//                } else {
//                    //不包括就退出循环
//                    break
//                }
            }
        } else {
            //拿到滚动item前显示的第一个item的前一个item，还是替换可见区域，实现向下的滑动回收复用
            val maxPos = getPosition(firstView!!) - 1
            for (i in maxPos downTo 0) {
                insert(i, visibleRect, recycler, true)
//                val rect = mItemsRect[i]
//                if (Rect.intersects(visibleRect, rect)) {
//                    val childView = recycler.getViewForPosition(i)
//                    //如果在显示区域，就插入第一个位置
//                    addView(childView, 0)
//                    measureChildWithMargins(childView, 0, 0)
//                    layoutDecorated(childView, rect.left, rect.top - mSumDy, rect.right, rect.bottom - mSumDy)
//                } else {
//                    break
//                }
            }
        }
//        mSumDy += travel
//        //平移容器中的item，向上移动要减去dy，所有直接平移负的值
//        offsetChildrenVertical(-travel)
        return travel
    }

    private fun insert(pos: Int, visibleRect: Rect, recycler: RecyclerView.Recycler, firstPos: Boolean) {
        val rect = mItemsRect[pos]
        //添加判断是否已经布局，没布局item进行布局
        if (Rect.intersects(visibleRect, rect) && !mHasAttachedItems[pos]) {
            val childView = recycler.getViewForPosition(pos)
            if (firstPos) {
                addView(childView, 0)
            } else {
                addView(childView)
            }
            measureChildWithMargins(childView, 0, 0)
            layoutDecoratedWithMargins(childView, rect.left, rect.top - mSumDy, rect.right, rect.bottom - mSumDy)
            //布局item之后，修改每个item旋转角度
            childView.rotationY = childView.rotationY + 1
            mHasAttachedItems.put(pos, true)
        }
    }

    /**
     * mSumDy表示上次移动距离，travel表示这次移动距离，mSumDy +travel表示这次移动后的距离，拿到移动后的屏幕与初始化item就行取交集，如果有交集就在这个区域内
     */
    private fun getVisibleArea(travel: Int): Rect {
        return Rect(
            paddingLeft,
            paddingTop + mSumDy + travel,
            width + paddingRight,
            getVerticalSpace() + mSumDy + travel
        )
    }

    /**
     * 改变item角度，透明度使用的方法
     * 我们在所有的布局操作前，先将移动距离mSumDy进行了累加。因为后面我们在布局item时，会弃用offsetChildrenVertical(-travel)移动item，
     * 而是在布局item时，就直接把item布局在新位置。最后，因为我们已经累加了mSumDy，所以我们需要改造getVisibleArea()，
     * 将原来getVisibleArea(int dy)中累加dy的操作去掉，归根结底就是不在使用offsetChildrenVertical(-travel)进行移动item，所以就无需travel距离
     */
    private fun getVisibleArea(): Rect {
        return Rect(paddingLeft, paddingTop + mSumDy, paddingRight + width, getVerticalSpace() + mSumDy)
    }

}