package com.wanandroid.zhangtianzhu.kotlinproject.utils

import android.content.Context
import android.graphics.*
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.wanandroid.zhangtianzhu.kotlinproject.R
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth


/**
 * @tips 在分割线绘制过程中，先调用分割线的onDraw方法，在调用item自身的onDraw方法，最后调用分割线的onDrawOver，所以item绘制内容会覆盖分割线的绘制内容，
 * 而分割线绘制图层蒙版方法是最后调用，在一切绘制之上
 */
class LinearItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val paint = Paint()
    private var mBitmap: Bitmap

    init {
        paint.isAntiAlias = true
        paint.color = Color.GREEN
        val options = BitmapFactory.Options()
        //在超出getItemOffsets函数所设定的outRect范围的部分将是不可见的。这是因为在整个绘制流程中，是选调用ItemDecoration的onDraw函数，
        // 然后再调用Item的onDraw函数，最后调用ItemDecoration的onDrawOver函数。
        //所以在ItemDecoration的onDraw函数中绘制的内容，当超出边界时，会被Item所覆盖。但是因为最后才调用ItemDecoration的OnDrawOver函数，
        // 所以在onDrawOver中绘制的内容就不受outRect边界的限制，可以覆盖Item的区域显示
//        options.inSampleSize = 2
        mBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.yz, options)
    }

    /**
     * 在getItemOffsets方法撑开的区域，进行绘制图像
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        //动态获取getItemOffsets outRect撑开的距离
        val layoutManager = parent.layoutManager
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
//            val cx = 100f
//            val left = layoutManager!!.getLeftDecorationWidth(view).toFloat()
//            val cx = left / 2
//            val cy = view.top + view.height / 2.toFloat()
//            c.drawCircle(cx, cy, 20f, paint)
            //绘制图片
            c.drawBitmap(mBitmap, 0f, view.top.toFloat(), paint)
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val childCount = parent.childCount
        val manager = parent.layoutManager
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            //获取当前子view的位置
            val index = parent.getChildAdapterPosition(view)
            val left = manager!!.getLeftDecorationWidth(view)
            //画勋章
            if (index % 5 == 0) {
                c.drawBitmap(mBitmap, (left - mBitmap.width / 2).toFloat(), view.top.toFloat(), paint)
            }
        }
        //画蒙版
        val firstView = parent.getChildAt(0)
        val gradient = LinearGradient(
            (parent.width / 2).toFloat(), 0f, (parent.width / 2).toFloat(), firstView.height.toFloat() * 3,
            -0xffff01, 0x000000ff, Shader.TileMode.CLAMP
        )
        paint.shader = gradient
        c.drawRect(0f, 0f, parent.width.toFloat(), firstView.height.toFloat() * 3, paint)
    }

    /**
     * 该方法类似于margin，给item四周加上边距
     * 第一个参数表示上下左右撑开一段距离
     * 第二个参数当前itemview对象
     * 第三个参数RecyclerView本身
     * 第四个参数获取当前RecyclerView状态，也可以在RecyclerView各状态之间传递参数
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = 200
//        outRect.right = 100
        //item上面撑开距离为1
        outRect.top = 1
    }

}