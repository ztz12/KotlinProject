package com.wanandroid.zhangtianzhu.kotlinproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class WaterfallLayout extends ViewGroup {

    /**
     * 设置每行显示的列数
     */
    private int colums = 3;
    /**
     * 设置子控件水平间距
     */
    private int hSpace = 20;
    /**
     * 设置垂直间距
     */
    private int vSpace = 20;
    /**
     * 保存每个子控件的高度，找出最短高度，并把新增的控件放在当前位置
     */
    private int[] top;

    public WaterfallLayout(Context context) {
        this(context, null);
    }

    public WaterfallLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterfallLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        top = new int[colums];
    }

    /**
     * 保存边距，避免每次计算宽度
     */
    public static class WaterfallLayoutParams extends ViewGroup.LayoutParams {

        public int left = 0;
        public int top = 0;
        public int right = 0;
        public int bottom = 0;

        public WaterfallLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public WaterfallLayoutParams(int width, int height) {
            super(width, height);
        }

        public WaterfallLayoutParams(LayoutParams source) {
            super(source);
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new WaterfallLayoutParams(WaterfallLayoutParams.WRAP_CONTENT, WaterfallLayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new WaterfallLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new WaterfallLayoutParams(getContext(), attrs);
    }

    /**
     * 即ViewGroup.LayoutParams不为空，就不会再走generateLayoutParams(params)函数，也就没办法使用我们自定义LayoutParams。所以我们必须重写，
     * 当LayoutParams不是WaterfallLayoutParams时，就需要进入generateLayoutParams函数，以使用自定义布局参数
     *
     * @param p
     * @return
     */
    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof WaterfallLayoutParams;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //计算控件所占宽度
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //获取单个item宽度
        int childWidth = (sizeWidth - (colums - 1) * hSpace) / colums;

        //得到总宽度，分为当前子view没有占满一行，或者占满一行就是sizeWidth
        int wrapWidth = 0;
        int count = getChildCount();
        if (count < colums) {
            wrapWidth = count * childWidth + (count - 1) * hSpace;
        } else {
            wrapWidth = sizeWidth;
        }

        clearTop();
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            //由于要保证每张图片宽度相同，将图片伸缩到指定宽度再得到对应的高度
            int childHeight = childView.getMeasuredHeight() * childWidth / childView.getMeasuredWidth();
            //每次获取最小列，进行布局
            int minColumn = getMinHeightColumn();

            //保存当前最短列的位置，用来放置控件，避免轮询操作耗时，直接在onMeasure中保存位置，在onLayout中获取
            WaterfallLayoutParams lp = (WaterfallLayoutParams) childView.getLayoutParams();
            lp.left = minColumn * (childWidth + hSpace);
            lp.top = top[minColumn];
            lp.right = lp.left + childWidth;
            lp.bottom = lp.top + childHeight;

            //得到最短列后，将当前控件放到最短列，再计算下一个控件位置，并放到最短列
            top[minColumn] += vSpace + childHeight;
        }
        int wrapHeight = getMaxHeight();
        setMeasuredDimension(widthModel == MeasureSpec.AT_MOST ? wrapWidth : sizeWidth, wrapHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            WaterfallLayoutParams lp = (WaterfallLayoutParams) childView.getLayoutParams();
            childView.layout(lp.left, lp.top, lp.right, lp.bottom);
        }
    }

    /**
     * 每次计算高度前，先进行情况高度数组，避免之前结果影响下次
     */
    private void clearTop() {
        for (int i = 0; i < top.length; i++) {
            top[i] = 0;
        }
    }

    /**
     * 获取高度最小的列
     *
     * @return
     */
    private int getMinHeightColumn() {
        int minColumn = 0;
        for (int i = 0; i < top.length; i++) {
            if (top[i] < top[minColumn]) {
                minColumn = i;
            }
        }
        return minColumn;
    }

    /**
     * 获取最大的高度就是整个控件应有的值
     *
     * @return
     */
    private int getMaxHeight() {
        int maxHeight = 0;
        for (int i = 0; i < top.length; i++) {
            if (top[i] > maxHeight) {
                maxHeight = top[i];
            }
        }
        return maxHeight;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int index);
    }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        for (int i = 0; i < getChildCount(); i++) {
            final int index = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, index);
                }
            });
        }
    }
}