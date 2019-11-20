package com.wanandroid.zhangtianzhu.kotlinproject.utils

import android.view.View

interface IRefreshHeader {

    /**
     * 正常状态
     */
    val STATE_NORMAL: Int
        get() = 0

    /**
     * 下拉状态
     */
    val STATE_RELEASE_TO_REFRESH: Int
        get() = 1

    /**
     * 刷新状态
     */
    val STATE_REFRESHING: Int
        get() = 2

    /**
     * 刷新完成状态
     */
    val STATE_DOWN: Int
        get() = 3

    fun onReset()

    /**
     * 处于可以刷新的状态
     */
    fun onPrepare()

    /**
     * 下拉移动
     */
    fun onMove(offset: Float, sumOffset: Float)

    /**
     * 下拉松开
     */
    fun onRelease(): Boolean

    /**
     * 刷新完成
     */
    fun onRefreshComplete()

    /**
     * 获取headview
     */
    fun getHeadView():View

    /**
     * 获取header显示高度
     */
    fun getVisibleHeight():Int
}