package com.wanandroid.zhangtianzhu.kotlinproject.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class ShimmerTextView extends AppCompatTextView {
    private Paint mPaint;
    private LinearGradient linearGradient;
    private int mDx = 0;

    public ShimmerTextView(Context context) {
        this(context, null);
    }

    public ShimmerTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShimmerTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = getPaint();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 2 * getMeasuredWidth());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mDx = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setDuration(600);
        valueAnimator.start();

        linearGradient = new LinearGradient(-getMeasuredWidth(), 0, 0, 0, new int[]{
                getCurrentTextColor(), 0xff00ff00, getCurrentTextColor()
        }, new float[]{0f, 0.5f, 1f}, Shader.TileMode.CLAMP);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Matrix matrix = new Matrix();
        //通过矩阵来设置渐变位移
        matrix.setTranslate(mDx, 0);
        linearGradient.setLocalMatrix(matrix);
        mPaint.setShader(linearGradient);
        super.onDraw(canvas);
    }
}
