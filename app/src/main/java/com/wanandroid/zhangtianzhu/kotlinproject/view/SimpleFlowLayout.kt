package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList

/**
 * 标签流式布局
 */
class SimpleFlowLayout constructor(context: Context, attributeSet: AttributeSet?) : ViewGroup(context, attributeSet) {

    // 保存每行的每个View
    private val mRowViewList = ArrayList<List<View>>()
    // 每行的高度
    private val mRowHeightList = ArrayList<Int>()
    // 当前行的View
    private var mCurLineViewList: MutableList<View> = ArrayList()

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        //重绘两次，需要清理
        mRowViewList.clear()
        mRowHeightList.clear()
        mCurLineViewList.clear()

        //取出自身的宽高，才能对子view进行测量宽高
        val widthModel = MeasureSpec.getMode(widthMeasureSpec)
        val heightModel = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec) - (paddingLeft + paddingRight)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        //已使用宽度
//        var useWidth = 0
//        //剩余宽度
//        var remainWidth = 0
//        //总高度
//        var totoalHeight = 0
        //当前行高
//        var lineHeight = 0

        //用于确定自身宽高
        var myselefMeasureWidth = 0
        var myselfMeasureHeight = 0

        //每一行宽高
        var lineWidth = 0
        var lineHeight = 0

        //measureChildren 是计算所有子view的宽高
//        measureChildren(widthMeasureSpec,heightMeasureSpec)
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            //先测量子view，让当前子view去绘制自己
//            measureChild(childView, widthMeasureSpec, heightMeasureSpec)

            //测量子view，减去padding与margin值
            measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0)

            //子视图真正大小需要加上其margin
            val lp = childView.layoutParams as MarginLayoutParams
            //获取子视图的宽高 childView.measuredWidth
            val childRealWidth = childView.measuredWidth + lp.leftMargin + lp.rightMargin
            val childRealHeight = childView.measuredHeight + lp.topMargin + lp.bottomMargin

            //进行判断当前子视图是否超过最大宽
            if (lineWidth + childRealWidth > widthSize) {
                //获取最大宽度值
                myselefMeasureWidth = Math.max(myselefMeasureWidth, lineWidth)
                //保存高
                myselfMeasureHeight += lineHeight

                //保存行view数据
                mRowViewList.add(mCurLineViewList)

                //保存行高
                mRowHeightList.add(lineHeight)

                //重新开辟list，保存新的view
                mCurLineViewList = ArrayList()
                mCurLineViewList.add(childView)

                //重置宽高
                lineWidth = childRealWidth
                lineHeight = childRealHeight
            } else {
                //保存行view
                mCurLineViewList.add(childView)

                //增加行宽，并保存行高最大值
                lineWidth += childRealWidth
                lineHeight = Math.max(lineHeight, childRealHeight)
            }

            //最后一行进行数据保存
            if (i == childCount - 1) {
                //加上padding
                myselefMeasureWidth = Math.max(myselefMeasureWidth, lineWidth) + paddingLeft + paddingRight
                myselfMeasureHeight += lineHeight

                mRowViewList.add(mCurLineViewList)
                mRowHeightList.add(lineHeight)
            }

//            //计算剩余宽度
//            remainWidth = widthSize - useWidth
//            //一行不够放，就另起一行存放子view
//            if (childView.measuredWidth > remainWidth) {
//                useWidth = 0
//                totoalHeight += lineHeight
//            }
//
//            //已使用宽度累加
//            useWidth += childView.measuredWidth
//            lineHeight = childView.measuredHeight
        }

//        if(heightModel==MeasureSpec.AT_MOST){
//            heightSize = totoalHeight
//        }

        // 如果是 match_parent 则直接使用 宽 高 尺寸
        if (widthModel == MeasureSpec.EXACTLY) {
            myselefMeasureWidth = widthSize + paddingLeft + paddingRight
        }

        if (heightModel == MeasureSpec.EXACTLY) {
            myselfMeasureHeight = heightSize
        } else {
            myselfMeasureHeight += paddingTop + paddingBottom
        }

        setMeasuredDimension(myselefMeasureWidth, myselfMeasureHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

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

//    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
//        var childTop = 0
//        var childLeft = 0
//        var childRight = 0
//        var childBottom = 0
//
//        var useWidth = 0
//        //获取可使用宽度
//        val layoutWidth = measuredWidth
//
//        for (i in 0 until childCount) {
//            val childView = getChildAt(i)
//            val lp = childView.layoutParams as MarginLayoutParams
//            //计算子view所需的宽高
//            val childWidth = childView.measuredWidth
//            val childHeight = childView.measuredHeight
//            //如果宽度不够，就另起一行
//            if (layoutWidth - useWidth < childWidth) {
//                useWidth = 0
//                childLeft = 0
//                childTop += childHeight
//                childRight = childWidth
//                childBottom = childTop + childHeight
//                childView.layout(
//                    childLeft + lp.leftMargin,
//                    childTop + lp.topMargin,
//                    childRight + lp.leftMargin,
//                    childBottom + lp.topMargin
//                )
//                useWidth += childWidth
//                childLeft = childWidth + lp.leftMargin + lp.rightMargin
//                childRight += lp.rightMargin
//                continue
//            }
//            childRight = childLeft + childWidth
//            childBottom = childTop + childHeight
//            childView.layout(
//                childLeft + lp.leftMargin,
//                childTop + lp.topMargin,
//                childRight + lp.leftMargin,
//                childBottom + lp.topMargin
//            )
//            childLeft += childWidth + lp.leftMargin + lp.rightMargin
//            childRight += lp.rightMargin
//            useWidth += childWidth + lp.leftMargin
//        }
//    }

}