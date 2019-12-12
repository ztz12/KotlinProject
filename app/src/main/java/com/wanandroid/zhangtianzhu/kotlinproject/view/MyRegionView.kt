package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class MyRegionView constructor(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val paint = Paint()
    //绘制一个矩形区域
    private val region = Region()
    private val ovalPath = Path()
    private val rectF = RectF(50f, 50f, 250f, 500f)
    private val regionRect = Region(50, 50, 250, 250)

    //构造两个矩形
    private val rect1 = Rect(100, 100, 200, 300)
    private val rect2 = Rect(0, 0, 300, 200)
    private val region1 = Region(rect1)
    private val region2 = Region(rect2)

    init {
        paint.color = Color.BLUE
        paint.strokeWidth = 5f
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //替换为新区域
//        region.set(100,100,300,500)
        ovalPath.addOval(rectF, Path.Direction.CCW)
        //传入一个比椭圆小的矩形区域 与前面的路径取交集，展示交集之后的区域
        region.setPath(ovalPath, regionRect)

        canvas.drawRect(rect1,paint)
        canvas.drawRect(rect2,paint)
        //取两个区域的交集
        region1.op(region2, Region.Op.INTERSECT)
        paint.color = Color.GREEN
        paint.style = Paint.Style.FILL_AND_STROKE
        drawRegion(canvas, region1, paint)
//        drawRegion(canvas, region, paint)


    }

    private fun drawRegion(canvas: Canvas, region: Region, paint: Paint) {
        //RegionIterator 实现获取组成矩形区域集的功能，根据区域构建矩形区域
        val regionIter = RegionIterator(region)
        val rect = Rect()
        while (regionIter.next(rect)) {
            canvas.drawRect(rect, paint)
        }
    }
}