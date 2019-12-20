package com.wanandroid.zhangtianzhu.kotlinproject.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 按钮水波纹效果
 */
public class RippleView extends AppCompatButton {
    private Paint paint;
    private float mDx;
    private float mDy;
    private int DEFAULT_RADIUS = 50;
    private ObjectAnimator objectAnimator;
    private int mCurRadius = 0;

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint = new Paint();
        paint.setAntiAlias(true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDx != event.getX() && mDy != event.getY()) {
            mDx = event.getX();
            mDy = event.getY();
            setRadius(DEFAULT_RADIUS);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP:
                if (objectAnimator != null && objectAnimator.isRunning()) {
                    objectAnimator.cancel();
                }

                if (objectAnimator == null) {
                    objectAnimator = ObjectAnimator.ofInt(this, "radius", DEFAULT_RADIUS, getWidth());
                }
                objectAnimator.setInterpolator(new AccelerateInterpolator());
                objectAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //动画结束让渐变半径为0
                        setRadius(0);
                    }
                });
                objectAnimator.start();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mDx, mDy, mCurRadius, paint);
    }

    private void setRadius(final int radius) {
        mCurRadius = radius;
        if (mCurRadius > 0) {
            //设置初始颜色透明度为0，显示出按钮的颜色，结束颜色为纯天空蓝颜色，空余部分使用CLAMP实现水波纹效果
            RadialGradient radialGradient = new RadialGradient(mDx, mDy, mCurRadius, 0x00FFFFFF, 0xFF58FAAC, Shader.TileMode.CLAMP);
            paint.setShader(radialGradient);
        }
        postInvalidate();
    }
}
