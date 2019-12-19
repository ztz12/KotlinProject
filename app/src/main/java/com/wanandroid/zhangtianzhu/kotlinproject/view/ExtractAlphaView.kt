package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.wanandroid.zhangtianzhu.kotlinproject.R


class ExtractAlphaView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val paint = Paint()
    private var mAlphaBitmap: Bitmap
    private var mBitmap: Bitmap
    private var mDx = 0
    private var mDy = 0
    private var mShaderColor = 0
    private var mRadius = 0f

    init {
        //为了防止自定义控件绘制问题，禁止硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        paint.isAntiAlias = true
        val option = BitmapFactory.Options()
        option.inSampleSize = 2

        //提取属性定义
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.ExtractAlphaView)
        val bitmapID = typeArray.getResourceId(R.styleable.ExtractAlphaView_src, -1)
        if (bitmapID == -1) {
            throw Exception("BitmapShadowView 需要定义Src属性,而且必须是图像")
        }
        mBitmap = BitmapFactory.decodeResource(context.resources, bitmapID)
        //extractAlpha 新建一个具有原图片样式的空白图像，空白图像背景由drawBitmap绘制来决定的
        mAlphaBitmap = mBitmap.extractAlpha()
        mDx = typeArray.getInt(R.styleable.ExtractAlphaView_shaderDx, 10)
        mDy = typeArray.getInt(R.styleable.ExtractAlphaView_shaderDy, 10)
        mShaderColor = typeArray.getColor(R.styleable.ExtractAlphaView_shaderColor, Color.RED)
        mRadius = typeArray.getFloat(R.styleable.ExtractAlphaView_shaderRadius, 10f)
        typeArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measureWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measureHeight = MeasureSpec.getSize(heightMeasureSpec)
        val measureWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val measureHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        val width = mBitmap.width
        val height = mBitmap.height
        setMeasuredDimension(
            if (measureWidthMode == MeasureSpec.EXACTLY) measureWidth else width,
            if (measureHeightMode == MeasureSpec.EXACTLY) measureHeight else height
        )
    }

    /**
     * 偏移原图形一段距离绘制出来
     * 先绘制阴影，再绘制原图形
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val alphaWidth = width - mDx
        val alphaHeight = alphaWidth * mAlphaBitmap.height / mAlphaBitmap.width

        paint.color = mShaderColor
//        canvas.drawBitmap(mBitmap,0f,0f,paint)
        paint.maskFilter = BlurMaskFilter(mRadius, BlurMaskFilter.Blur.NORMAL)
        canvas.drawBitmap(mAlphaBitmap, null, Rect(mDx, mDy, alphaWidth, alphaHeight), paint)

//        paint.color = Color.RED
//        canvas.drawBitmap(mAlphaBitmap, null, Rect(10, alphaHeight + 10, alphaWidth, alphaHeight * 2), paint)

        //取消发光效果
        paint.maskFilter = null
        canvas.drawBitmap(mBitmap, null, Rect(0, 0, alphaWidth, alphaHeight), paint)
//        canvas.drawBitmap(mBitmap, null, Rect(0, alphaHeight , alphaWidth, alphaHeight * 2), paint)
    }
}