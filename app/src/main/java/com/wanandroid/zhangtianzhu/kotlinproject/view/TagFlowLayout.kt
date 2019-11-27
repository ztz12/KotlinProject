package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList


class TagFlowLayout  constructor(context: Context, attrs: AttributeSet? = null) :
    ViewGroup(context, attrs) {

    private val TAG = "FlowLayout"

    // 测量次数
    private var onMeasureCount = 0
    // 摆放次数
    private var onLayoutCount = 0

    // 保存每行的每个View
    private val mRowViewList = ArrayList<List<View>>()
    // 每行的高度
    private val mRowHeightList = ArrayList<Int>()
    // 当前行的View
    private var mCurLineViewList: MutableList<View> = ArrayList()

    // 注意：必须要重写这个方法，因为子视图需要获取其 margin
    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(context, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        Log.i(TAG, "onMeasure: " + onMeasureCount++)

        // 会进行两次绘制，所以需要先进行清理
        mRowViewList.clear()
        mRowHeightList.clear()
        mCurLineViewList.clear()

        // 获取 自身的模式
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        // 获取 自身的宽高
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec) - (paddingLeft + paddingRight)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        // TagFlowLayout控件的宽高，用于确定自己的大小
        var myselfMeasureWidth = 0
        var myselfMeasureHeight = 0

        // 每一行的宽高
        var lineWidth = 0
        var lineHeight = 0

        // 获取子视图个数
        val childCount = childCount

        // 遍历获取子视图的信息
        for (i in 0 until childCount) {

            // 1、获取子视图
            val childView = getChildAt(i)

            // 2、子视图进行测量
            measureChildWithMargins(
                childView, widthMeasureSpec, 0,
                heightMeasureSpec, 0
            )

            // 3、获取子视图宽高
            val childWidth = childView.measuredWidth
            val childHeight = childView.measuredHeight

            // 4、子视图真正占的大小（需要加上其margin）
            // 此处要使用MarginLayoutParams，则必须重写generateLayoutParams方法
            val layoutParams = childView.layoutParams as ViewGroup.MarginLayoutParams
            val childRealWidth = childWidth + layoutParams.leftMargin + layoutParams.rightMargin
            val childRealHeight = childHeight + layoutParams.topMargin + layoutParams.bottomMargin

            // 进行判读是否 加上当前 子视图会 导致超出行宽
            if (lineWidth + childRealWidth > widthSize) {

                // 获取最大的宽值
                myselfMeasureWidth = Math.max(myselfMeasureWidth, lineWidth)
                // 保存高
                myselfMeasureHeight += lineHeight

                // 保存行的view数据
                mRowViewList.add(mCurLineViewList)
                // 保存行高
                mRowHeightList.add(lineHeight)

                // 重新开辟一个list，保存新的行view
                mCurLineViewList = ArrayList()
                mCurLineViewList.add(childView)

                // 重置行宽、高
                lineWidth = childRealWidth
                lineHeight = childRealHeight

            } else {

                // 保存行view
                mCurLineViewList.add(childView)
                // 增加行宽 和 保存 行高最大值
                lineWidth += childRealWidth
                lineHeight = Math.max(lineHeight, childRealHeight)

            }

            // 最后一行数据要进行保存
            if (i == childCount - 1) {
                // 加上 padding
                myselfMeasureWidth = (Math.max(myselfMeasureWidth, lineWidth)
                        + paddingRight + paddingLeft)

                myselfMeasureHeight += lineHeight
                mRowViewList.add(mCurLineViewList)
                mRowHeightList.add(lineHeight)
            }

        }

        // 如果是 match_parent 则直接使用 宽 高 尺寸
        if (widthMode == View.MeasureSpec.EXACTLY) {
            myselfMeasureWidth = widthSize + paddingRight + paddingLeft
        }

        if (heightMode == View.MeasureSpec.EXACTLY) {
            myselfMeasureHeight = heightSize
        } else {
            myselfMeasureHeight += paddingTop + paddingBottom
        }

        setMeasuredDimension(myselfMeasureWidth, myselfMeasureHeight)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        Log.i(TAG, "onLayout: " + onLayoutCount++)

        var left: Int
        var top: Int
        var right: Int
        var bottom: Int

        var curTop = paddingTop
        var curLeft = paddingLeft

        val lineSize = mRowViewList.size
        // 遍历每行
        for (i in 0 until lineSize) {

            val lineView = mRowViewList[i]
            val viewSize = lineView.size
            for (j in 0 until viewSize) {
                val view = lineView[j]
                val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams

                left = curLeft + layoutParams.leftMargin
                top = curTop + layoutParams.topMargin
                right = left + view.measuredWidth
                bottom = top + view.measuredHeight

                view.layout(left, top, right, bottom)

                curLeft += view.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin
            }

            // 每行重置
            curLeft = paddingLeft
            curTop += mRowHeightList[i]

        }

        mRowViewList.clear()
        mRowHeightList.clear()
    }

}
