package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

class MyLinearLayout(context: Context, attributeSet: AttributeSet) : ViewGroup(context, attributeSet) {

    private lateinit var lp:MarginLayoutParams

    //正常情况下generateLayoutParams重写不能获取对应的margin值，还需要重写generateDefaultLayoutParams
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context,attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var height = 0
        var width = 0
        val measureWidthModel = MeasureSpec.getMode(widthMeasureSpec)
        val measureHeightModel = MeasureSpec.getMode(heightMeasureSpec)
        val measureWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measureHeight = MeasureSpec.getSize(heightMeasureSpec)
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            //这里无法获取到子view的测量宽度，只能等到measureChild方法后测量完成才能获取子view测量宽度
//            val childT = childView.measuredWidth
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
            if(childView.layoutParams is MarginLayoutParams){
                lp = childView.layoutParams as MarginLayoutParams
            }
            val childWidth = childView.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = childView.measuredHeight + lp.topMargin + lp.bottomMargin
            //这里无法获取到子view的宽度，需要在子view调用onLayout方法后才能获取到
//            val childW = childView.width
            //得到最大宽度，并且累加高度
            height += childHeight
            width = Math.max(childWidth, width)
        }

        //测量完成后将值设置给系统使用
        setMeasuredDimension(
            if (measureWidthModel == MeasureSpec.EXACTLY) measureWidth else width,
            if (measureHeightModel == MeasureSpec.EXACTLY) measureHeight else height
        )
    }

    /**
     *  首先getMeasureWidth()方法在measure()过程结束后就可以获取到了，而getWidth()方法要在layout()过程结束后才能获取到。
     *  getMeasureWidth()方法中的值是通过setMeasuredDimension()方法来进行设置的，而getWidth()方法中的值则是通过layout(left,top,right,bottom)方法设置的。
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var top = 0
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            if(child.layoutParams is MarginLayoutParams){
                lp = child.layoutParams as MarginLayoutParams
            }
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin
            child.layout(0, top, childWidth, childHeight + top)

            //这里可以获取到子view的宽度，因为已经调用完了onLayout方法
//            val childWt = child.width
            top += childHeight
        }
    }
}