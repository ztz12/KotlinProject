package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * 画波浪
 */
class WaveView constructor(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet) {
    private val paint = Paint()
    private val path = Path()
    private var mBeforeX = 0f
    private var mBeforeY = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        paint.color = Color.RED

//        path.moveTo(200f, 400f)
        //一个为控制点，另一个为终点
//        path.quadTo(300f, 300f, 400f, 400f)
//
//        path.quadTo(500f, 500f, 600f, 400f)

        /**
         * 使用rQuadTo 来实现波浪效果
         * x1：控制点的X坐标，表示相对于上一个终点X坐标的位移值，可以为负值，正值表示相加，负值表示相减
        x2：控制点的Y坐标，表示相对于上一个终点Y坐标的位移值，可以为负值，正值表示相加，负值表示相减
        x2：终点的X坐标，表示相对于上一个终点X坐标的位移值，可以为负值，正值表示相加，负值表示相减
        y2：终点的Y坐标，表示相对一上一个终点Y坐标的位移值，可以为负值，正值表示相加，负值表示相减
        这么说可能不理解，下面还是直接举例子：
        如果上一个终点坐标是(100,200)，如果这时候调用rQuardTo(100,-100,200,200)，得到的控制点坐标是(100 + 100,200 - 100 )就是(200,100)，
        得到的终点坐标是(100 + 200,200 + 200)就是(300,400)
        下面代码等价
        path.moveTo(100,200);
        path.quadTo(200,100,300,400);
        与
        path.moveTo(100,200);
        path.rQuadTo(100,-100,200,200);
         */
        path.moveTo(200f, 400f)
        //终点 400，400
        path.rQuadTo(100f, -100f, 200f, 0f)
        path.rQuadTo(100f, 100f, 200f, 0f)
        canvas.drawPath(path, paint)
    }

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                path.moveTo(event.x, event.y)
//                //保存点坐标
//                mBeforeX = event.x
//                mBeforeY = event.y
//                //ACTION_DOWN 事件返回true被消费，后面的move事件才能被接收处理
//                return true
//            }
//            MotionEvent.ACTION_MOVE -> {
////                path.lineTo(event.x, event.y)
//                //使用二阶贝塞尔曲线平滑过渡，获取线段终点为线段的中点坐标
//                val endX = (mBeforeX + event.x) / 2
//                val endY = (mBeforeY + event.y) / 2
//                path.quadTo(mBeforeX, mBeforeY, endX, endY)
//
//                //更新点坐标
//                mBeforeX = event.x
//                mBeforeY = event.y
//                invalidate()
//            }
//        }
//        return super.onTouchEvent(event)
//    }

    fun reset() {
        path.reset()
        invalidate()
    }
}