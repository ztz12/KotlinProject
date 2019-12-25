package com.wanandroid.zhangtianzhu.kotlinproject.view

import android.content.Context
import android.hardware.SensorManager
import android.util.AttributeSet
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView
import com.wanandroid.zhangtianzhu.kotlinproject.utils.GalleryLayoutManager

class GalleryRecyclerView(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {

    init {
        //开启改变item绘制顺序
        isChildrenDrawingOrderEnabled = true
    }

    /**
     * 获取layoutManager 并强制为GalleryLayoutManager
     */
    fun getRecyLayoutManager(): GalleryLayoutManager {
        return layoutManager as GalleryLayoutManager
    }

    /**
     * 更改item的绘制顺序
     * 第一个参数表示当前屏幕上面可见item数量
     * 第二个参数表示当前可见item的索引，通过getChatAt(i)获取当前item的视图
     * 返回值表示当前item的绘制顺序，返回值越小表示越先绘制，越大表示越后绘制
     * 中间位置的绘图顺序为order = childCount -1;
    中间位置之前的item的绘图顺序为 order = i;
    中间位置之后的item的绘图顺序为 order = center + childCount - i - i;
     */
    override fun getChildDrawingOrder(childCount: Int, i: Int): Int {
        //计算正在显示所有item的中心位置
        val center = getRecyLayoutManager().getCenterPos() - getRecyLayoutManager().getFirstVisiblePos()
        var order = 0
        if (i == center) {
            order = childCount - 1
        } else if (i > center) {
            order = center + childCount - i - 1
        } else {
            order = i
        }

        return order
    }

    /**
     * fling 校正
     * 第一个参数表示横向滑动，系统根据这个计算出横向滑动距离，velocityX>0表示向右滑动，小于0向左滑动
     * 第二个参数表示纵向滑动，系统根据这个计算出纵向滑动距离，velocityY>0 表示向下滑动，小于0表示向上滑动
     */
    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        //缩小滑动距离
        var flingX = velocityX * 0.4.toInt()
        val manager = getRecyLayoutManager()
        //根据滑动系数得到对应的滑动距离
        val distance = getSplineFlingDistance(flingX)
        //原始距离校正
        val newDistance = manager.calculateDistance(velocityX, distance)
        //根据新的距离计算出新的滚动系数
        val fixVelocityX = getVelocity(newDistance)
        //对新的滚动系数进行正负校正
        flingX = if (velocityX > 0) {
            fixVelocityX
        } else {
            -fixVelocityX
        }
        //返回给系统使用滚动系数
        return super.fling(flingX, velocityY)
    }

//    -----------以下代码从系统自带的android.widget.OverScroller.java里抽出来的-----------------------------------------------------

    /**
     * 根据松手后的滑动速度计算出fling的距离
     *
     * @param velocity
     * @return
     */
    private fun getSplineFlingDistance(velocity: Int): Double {
        val l = getSplineDeceleration(velocity)
        val decelMinusOne = DECELERATION_RATE - 1.0
        return mFlingFriction.toDouble() * getPhysicalCoeff().toDouble() * Math.exp(DECELERATION_RATE / decelMinusOne * l)
    }

    /**
     * 根据距离计算出速度
     *
     * @param distance
     * @return
     */
    private fun getVelocity(distance: Double): Int {
        val decelMinusOne = DECELERATION_RATE - 1.0
        val aecel = Math.log(distance / (mFlingFriction * mPhysicalCoeff)) * decelMinusOne / DECELERATION_RATE
        return Math.abs((Math.exp(aecel) * (mFlingFriction * mPhysicalCoeff) / INFLEXION).toInt())
    }

    /**
     * --------------flling辅助类---------------
     */
    private val INFLEXION = 0.35f // Tension lines cross at (INFLEXION, 1)
    private val mFlingFriction = ViewConfiguration.getScrollFriction()
    private val DECELERATION_RATE = (Math.log(0.78) / Math.log(0.9)).toFloat()
    private var mPhysicalCoeff = 0f

    private fun getSplineDeceleration(velocity: Int): Double {
        val ppi = this.resources.displayMetrics.density * 160.0f
        val mPhysicalCoeff = (SensorManager.GRAVITY_EARTH // g (m/s^2)

                * 39.37f // inch/meter

                * ppi
                * 0.84f) // look and feel tuning


        return Math.log((INFLEXION * Math.abs(velocity) / (mFlingFriction * mPhysicalCoeff)).toDouble())
    }

    private fun getPhysicalCoeff(): Float {
        if (mPhysicalCoeff == 0f) {
            val ppi = this.resources.displayMetrics.density * 160.0f
            mPhysicalCoeff = (SensorManager.GRAVITY_EARTH // g (m/s^2)

                    * 39.37f // inch/meter

                    * ppi
                    * 0.84f) // look and feel tuning
        }
        return mPhysicalCoeff
    }
}