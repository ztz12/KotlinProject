package com.wanandroid.zhangtianzhu.kotlinproject.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class LinearGradientView extends View {
    private Paint mPaint;
    private int[] colors = {0xffff0000, 0xff00ff00, 0xff0000ff, 0xffffff00, 0xff00ffff};
    /**
     * 前面四种颜色按照20%百分比过度，最后两种颜色按照40%百分比过度
     */
    private float[] pos = {0f, 0.2f, 0.4f, 0.6f, 1.0f};


    public LinearGradientView(Context context) {
        super(context);
        init();
    }

    public LinearGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LinearGradientView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //(x0,y0)就是起始渐变点坐标，参数中(x1,y1)就是结束渐变点坐标
        //colors[]用于指定渐变的颜色值数组，同样，颜色值必须使用0xAARRGGBB形式的16进制表示！表示透明度的AA一定不能少。
        //positions[]与渐变的颜色相对应，取值是0-1的float类型，表示在每一个颜色在整条渐变线中的百分比位置
//        mPaint.setShader(new LinearGradient(0, getHeight() / 2, getWidth(), getHeight() / 2, colors,pos, Shader.TileMode.CLAMP));

        //渐变修改成从左上角到右下角的填充渐变
//        mPaint.setShader(new LinearGradient(0, 0, getWidth(), getHeight(), colors, pos, Shader.TileMode.CLAMP));

//        mPaint.setShader(new LinearGradient(0,0,getWidth()/2,getHeight()/2,colors,pos, Shader.TileMode.CLAMP));
//        mPaint.setShader(new LinearGradient(0,0,getWidth()/2,getHeight()/2,colors,pos, Shader.TileMode.REPEAT));
        mPaint.setShader(new LinearGradient(0,0,getWidth()/2,getHeight()/2,colors,pos, Shader.TileMode.MIRROR));

        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }
}
