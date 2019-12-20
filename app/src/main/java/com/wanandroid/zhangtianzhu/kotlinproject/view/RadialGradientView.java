package com.wanandroid.zhangtianzhu.kotlinproject.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class RadialGradientView extends View {

    private Paint paint;
    /**
     * 放射性渐变，像放射源一样，从一个开始点向外，一个颜色渐变另一个颜色
     */
    private RadialGradient radialGradient;
    private int mRadius = 100;
    private int[] colors = new int[]{0xffff0000,0xff00ff00,0xff0000ff,0xffffff00};
    //使用多色渐变一般第一个数值与最后一个数值取值分别为0与1，取值数量必须与颜色数组中的个数一直，否则直接崩溃
    private float[] stops  = new float[]{0f,0.2f,0.5f,1f};

    public RadialGradientView(Context context) {
        this(context, null);
    }

    public RadialGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadialGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    /**
     * 在布局发送改变时调用
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //参数分别是：渐变中心坐标，渐变半径，渐变起始颜色与结束颜色，渐变设置的填充空白区域图像的模式
        radialGradient = new RadialGradient(w / 2, h / 2, mRadius, 0xffff0000, 0xff00ff00, Shader.TileMode.REPEAT);
        //多色渐变
//        radialGradient = new RadialGradient(w/2,h/2,mRadius,colors,stops, Shader.TileMode.REPEAT);
        paint.setShader(radialGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, paint);
        canvas.drawRect(0,0,getWidth(),getHeight(),paint);
    }
}
