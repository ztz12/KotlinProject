package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.wanandroid.zhangtianzhu.kotlinproject.adapter.RefreshHeaderAdapter
import com.wanandroid.zhangtianzhu.kotlinproject.utils.IRefreshHeader
import com.wanandroid.zhangtianzhu.kotlinproject.utils.OnRefreshListener

class RefreshHeaderRecyclerView constructor(context: Context, attributeSet: AttributeSet) :
    RecyclerView(context, attributeSet) {
    private var mLastY: Float = -1f
    private var sumOffset: Float = 0f
    private lateinit var mRefreshHeader: IRefreshHeader
    private var mRefreshing = false
    private lateinit var mRefreshAdapter: RefreshHeaderAdapter
    private var mRefreshListener: OnRefreshListener? = null

    fun setRefreshAdapter(refreshAdapter: RefreshHeaderAdapter) {
        mRefreshAdapter = refreshAdapter
        mRefreshHeader = ArrowRefreshHeader(context.applicationContext, null)
        mRefreshAdapter.setRefreshHeader(mRefreshHeader)
        super.setAdapter(refreshAdapter)
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        if (mLastY == -1f) {
            mLastY = e.rawY
        }
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastY = e.rawY
                sumOffset = 0f
            }
            MotionEvent.ACTION_MOVE -> {
                //为了防止滑动幅度过大，将实际手指滑动距离除以2
                val deltalY = (e.rawY - mLastY) / 2
                mLastY = e.rawY
                //计算总的滑动距离
                sumOffset += deltalY
                if (isTop() && !mRefreshing) {
                    mRefreshHeader.onMove(deltalY, sumOffset)
                    if (mRefreshHeader.getVisibleHeight() > 0) {
                        return false
                    }
                }
            }
            else -> {
                mLastY = -1f
                if (isTop() && !mRefreshing) {
                    if (mRefreshHeader.onRelease()) {
                        //手指松开
                        if (mRefreshListener != null) {
                            mRefreshing = true
                            mRefreshListener!!.onRefresh()
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(e)
    }

    private fun isTop(): Boolean {
        return mRefreshHeader.getHeadView().parent != null
    }

    fun setOnRefreshListener(listener: OnRefreshListener) {
        this.mRefreshListener = listener
    }

    fun refreshComplete() {
        if (mRefreshing) {
            mRefreshing = false
            mRefreshHeader.onRefreshComplete()
        }
    }
}