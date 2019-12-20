package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

class FlowLayout(context: Context, attributeSet: AttributeSet) : ViewGroup(context, attributeSet) {

    private lateinit var lp: MarginLayoutParams
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measureWidthModel = MeasureSpec.getMode(widthMeasureSpec)
        val measureHeightModel = MeasureSpec.getMode(heightMeasureSpec)
        val measureWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measureHeight = MeasureSpec.getSize(heightMeasureSpec)

        //记录每一行的行宽
        var lineWidth = 0
        //记录每一行的行高
        var lineHeight = 0
        //记录总体的FlowLayout的宽高
        var flowWidth = 0
        var flowHeight = 0
        val count = childCount
        for (i in 0 until count) {
            val childView = getChildAt(i)
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)

            if (childView.layoutParams is MarginLayoutParams) {
                lp = childView.layoutParams as MarginLayoutParams
            }
            val childWidth = childView.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = childView.measuredHeight + lp.topMargin + lp.bottomMargin
            //如果当前宽度大于整个布局宽度，就进行换行处理
            if (lineWidth + childWidth > measureWidth) {
                flowWidth = Math.max(lineWidth, childWidth)
                flowHeight += lineHeight

                //重新设置 因为由于盛不下当前控件，而将此控件调到下一行，所以将此控件的高度和宽度初始化给lineHeight、lineWidth
                lineWidth = childWidth
                lineHeight = childHeight
            } else {
                //当计算到最后一行时候，肯定不会超过行宽，只是对行宽与行高进行处理，还需要加上之前计算的宽高
                //否则累加子view宽度，设置子view最大高度
                lineHeight = Math.max(lineHeight, childHeight)
                lineWidth += childWidth
            }

            //最后一行不会超过width范围，单独处理
            if (i == count - 1) {
                flowHeight += lineHeight
                flowWidth = Math.max(lineWidth, childWidth)
            }
        }

        //测量完成后将值设置给系统使用
        setMeasuredDimension(
            if (measureWidthModel == MeasureSpec.EXACTLY) measureWidth else flowWidth,
            if (measureHeightModel == MeasureSpec.EXACTLY) measureHeight else flowHeight
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var lineWidth = 0
        var lineHeight = 0
        var top = 0
        var left = 0
        val count = childCount
        for (i in 0 until count) {
            val childView = getChildAt(i)
            if (childView.layoutParams is MarginLayoutParams) {
                lp = childView.layoutParams as MarginLayoutParams
            }
            val childWidth = childView.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = childView.measuredHeight + lp.topMargin + lp.bottomMargin
            if (lineWidth + childWidth > measuredWidth) {
                //由于进行换行操作，left重新设置为0
                left = 0
                top += childHeight

                lineWidth = childWidth
                lineHeight = childHeight
            } else {
                lineHeight = Math.max(lineHeight, childHeight)
                lineWidth += childWidth
            }
            //计算子view的left top right bottom
            val lc = left + lp.leftMargin
            val tc = top + lp.topMargin
            val rc = lc + childView.measuredWidth
            val bc = tc + childView.measuredHeight

            childView.layout(lc, tc, rc, bc)

            //设置下一个控件起始点
            left += childWidth
        }
    }
}